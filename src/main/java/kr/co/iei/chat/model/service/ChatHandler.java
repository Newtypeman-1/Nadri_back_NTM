package kr.co.iei.chat.model.service;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.iei.chat.model.dto.ChatContent;
import kr.co.iei.chat.model.dto.ChatDTO;
import kr.co.iei.chat.model.dto.ChatRoomDTO;


@Component
public class ChatHandler extends TextWebSocketHandler {

	@Autowired
	private ChatService chatService;
	private ObjectMapper om;
	// 로그인한 사람이 있는 채팅방 관리
	private HashMap<Integer, Set<String>> chatRooms;
	// List를 순회하여  loginMembers 에 있으면 해당 소켓으로 데이터 보내줌
	private HashMap<String, WebSocketSession> loginMembers;
	public ChatHandler() {
		super();
		loginMembers = new HashMap<>();
		chatRooms = new HashMap<>();
		om = new ObjectMapper();
	}
	
	// url 에서 닉네임 추출해오는 메소드
	private String getMemberNickname(String query) {
	    if (query == null || !query.startsWith("memberNickname=")) return null;
	    String encoded = query.substring("memberNickname=".length());
	    return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
	}
	//클라이언트에 메세지 전송함수
	private void sendMessage(Object obj,WebSocketSession session) throws IOException {
		String data =om.writeValueAsString(obj) ;
		TextMessage sendData = new TextMessage(data);
		session.sendMessage(sendData);
	}
	// 클라이언트가 소켓에 최초 접속하면 자동으로 실행되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 로그인한 사람 그룹 로드
		URI uri = session.getUri();
		String memberNickname = getMemberNickname(uri.getQuery());
		// 로그인한 세션 저장
		loginMembers.put(memberNickname,session);
		//해당 닉넴이으로 있는 채팅방 조회
		ArrayList<Integer> roomList = (ArrayList<Integer>)chatService.selectRoomList(memberNickname);
		// 채팅방 조회후 있으면 chatRooms 에 추가
		if(!roomList.isEmpty()) {
			for(int chatNo : roomList) {
				//채팅방에 속한 사람들 조회
				Set groupSet = chatService.selectGroupSet(chatNo);
				chatRooms.put(chatNo, groupSet);
			}
		}
	}
	// 클라이언트가 소켓으로 데이터를 전송하면 실행되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	    ChatDTO chat = om.readValue(message.getPayload(), ChatDTO.class);
	    String type = chat.getType();
	    switch (type) {
	        case "FETCH_ROOM_LIST":
	            handleFetchRoomList(session);
	            break;
	        case "SELECT_ROOM":
	        	int chatNo = chat.getChatNo();
	        	handleSelectRoom(chatNo);
	        	break;
	        case "SEND_MESSAGE":
	            handleSendMessage(session, chat);
	            break;
	        case "CREATE_ROOM":
	            handleCreateRoom(session, chat);
	            break;

	        default:
	            session.sendMessage(new TextMessage("알 수 없는 요청 타입: " + type));
	    }
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		URI uri = session.getUri();
	    String memberNickname = getMemberNickname(uri.getQuery());
	    // 세션 제거
	    
	    loginMembers.remove(memberNickname);
	}
	private void handleSelectRoom(int chatNo) throws IOException {
		List<ChatContent> chatContent = chatService.selectChatContent(chatNo);
        Map<String, Object> map = new HashMap<>();
        map.put("type", "CHAT_CONTENT");
        map.put("content", chatContent);
        //해당 채팅방으로 로그인한 닉네임 불러옴
        Set<String> nickSet = chatRooms.get(chatNo);
        if (nickSet != null) {
            for (String nick : nickSet) {
                WebSocketSession target = loginMembers.get(nick);
                if (target != null && target.isOpen()) {
                    sendMessage(map, target);
                }
            }
        }
	}
	//메시지 전송받았을 때 작업
	private void handleSendMessage(WebSocketSession session, ChatDTO chat) throws Exception {
	    URI uri = session.getUri();
	    String memberNickname = getMemberNickname(uri.getQuery());
	    int chatNo = chat.getChatNo();
	    ChatContent cc = new ChatContent(chatNo, memberNickname, null, chat.getMessage());
	    int result = chatService.insertText(cc);
	    if (result > 0) {
	    	handleSelectRoom(chatNo);
	    }
	}
	//방목록 최신화
	private void handleFetchRoomList(WebSocketSession session) throws Exception {
	    URI uri = session.getUri();
	    String memberNickname = getMemberNickname(uri.getQuery());
	    List<ChatRoomDTO> roomDataList = chatService.selectRoomData(memberNickname);
	    Map<String, Object> response = new HashMap<>();
	    response.put("type", "ROOM_LIST");
	    response.put("room", roomDataList);
	    sendMessage(response, session);
	}
}
