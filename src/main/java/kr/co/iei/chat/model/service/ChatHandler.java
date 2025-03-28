package kr.co.iei.chat.model.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.iei.chat.model.dto.ChatContent;
import kr.co.iei.chat.model.dto.ChatDTO;


@Component
public class ChatHandler extends TextWebSocketHandler {
	@Autowired
	private ChatService chatService;
	private ObjectMapper om;
	// 로그인 세션을 관리할 변수
	private HashMap<Integer, List<String>> loginGroup;
	
	private HashMap<String, WebSocketSession> loginMembers;
	

	// 채팅리스트 조회 함수
	public ChatHandler() {
		super();
		loginMembers = new HashMap<>();
		loginGroup = new HashMap<>();
		
		om = new ObjectMapper();
	}
	// url 에서 닉네임 추출해오는 메소드
	private String getMemberNickname(String query) {
	    if (query == null || !query.startsWith("memberNickname=")) return null;
	    String encoded = query.substring("memberNickname=".length());
	    return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
	}
	//클라이언트에 메세지변경 및 전달 함수
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
		loginMembers.put(memberNickname,session);
		//해당 닉네임이 속한 그룹 리스트 조회
		ArrayList<Integer> chatList = (ArrayList<Integer>)chatService.selectGroupList(memberNickname);
		// loginGroup 에 세션 추가
		for(int chatNo : chatList) {
			if(loginGroup.containsKey(chatNo)) {
				loginGroup.get(chatNo).add(memberNickname);
			}else {
				List nickList = new ArrayList<String>();
				nickList.add(memberNickname);
				loginGroup.put(chatNo, nickList);
			}	
		}
		//채팅방 조회
		List roomDataList = chatService.selectRoomData(chatList);
		HashMap<String, List> map = new HashMap<>();
		map.put("room", roomDataList);
		sendMessage(map, session);
	}
	// 클라이언트가 소켓으로 데이터를 전송하면 실행되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ChatDTO chat = om.readValue(message.getPayload(), ChatDTO.class);
		// 방에 대한 채팅 쭉 가져오는거라 대상 상관없음
		if (chat.getType().equals("select")) {
			List chatContent = chatService.selectChatContent(chat.getChatNo());
			HashMap<String, List> map = new HashMap<>();
			map.put("content", chatContent);
			sendMessage(map, session);
			//누가 입력하면 변경된거라 다 뿌려줘야함 -> 채팅 방에 있는 모든 대상세션
		}else if(chat.getType().equals("text")) {
			URI uri = session.getUri();
			String memberNickname = getMemberNickname(uri.getQuery());
			int chatNo = chat.getChatNo();
			ChatContent cc = new ChatContent(chatNo, memberNickname, null, chat.getMessage());
			int result = chatService.insertText(cc);
			if(result>0) {
				List chatContent = chatService.selectChatContent(chat.getChatNo());
				HashMap<String, List> map = new HashMap<>();
				map.put("content", chatContent);
				ArrayList<String> nickList = (ArrayList<String>)loginGroup.get(chatNo); 
				for(String nick : nickList) {
					sendMessage(map, loginMembers.get(nick));
				}
			}
		}
		
	}

	// 클라이언트가 소켓에서 접속이 끊어지면 자동으로 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		URI uri = session.getUri();
		String memberNickname = getMemberNickname(uri.getQuery());
		loginMembers.remove(memberNickname);
		for(int chatNo : loginGroup.keySet()) {
			List list = loginGroup.get(chatNo);
			list.remove(memberNickname);
		}
	}
}
