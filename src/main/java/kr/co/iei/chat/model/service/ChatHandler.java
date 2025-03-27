package kr.co.iei.chat.model.service;

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

import kr.co.iei.chat.model.dto.ChatDTO;
import kr.co.iei.chat.model.dto.ChatRoomDTO;

@Component
public class ChatHandler extends TextWebSocketHandler {
	@Autowired
	private ChatService chatService;
	private ObjectMapper om;
	// 로그인 세션을 관리할 변수
	private HashMap<Integer, List<WebSocketSession>> loginGroup;
	private HashMap<WebSocketSession, List<ChatRoomDTO>> allChatList;

	// 채팅리스트 조회 함수
	public ChatHandler() {
		super();
		allChatList = new HashMap<>();
		om = new ObjectMapper();
	}

	// 닉네임으로 채팅방 리스트 조회해오는 메소드
	private List<ChatRoomDTO> selectChatList(String nickname) {
		
		return null;
	}
	// url 에서 닉네임 추출해오는 메소드
	private String getMemberNickname(String query) {
	    if (query == null || !query.startsWith("memberNickname=")) return null;

	    String encoded = query.substring("memberNickname=".length());
	    return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
	}
	// 클라이언트가 소켓에 최초 접속하면 자동으로 실행되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 로그인한 사람 그룹 로드
		URI uri = session.getUri();
		String memberNickname = getMemberNickname(uri.getQuery());
		//해당 닉네임이 속한 그룹 리스트 조회
		List groupList = chatService.selectGroupList(memberNickname);
		System.out.println(groupList);
	}

	// 클라이언트가 소켓으로 데이터를 전송하면 실행되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ChatDTO chat = om.readValue(message.getPayload(), ChatDTO.class);
		if (chat.getType().equals("list")) {
			List<ChatRoomDTO> list = selectChatList(chat.getMemberNickname());
			allChatList.put(session, list);
		}
	}

	// 클라이언트가 소켓에서 접속이 끊어지면 자동으로 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

	}

}
