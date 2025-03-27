package kr.co.iei.chat.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.co.iei.chat.model.dto.ChatListDTO;

@Component
public class ChatHandler extends TextWebSocketHandler {
	//접속한 회원 정보를 저장할 컬렉션
	// 배열이 적합한 경우 -> 응답을 전체 다 보내야하는 경우
	// 맵이 적합한 경우 - > 특정 대상에게 보내야 하는 경우
	private HashMap<WebSocketSession, String> members;
	//채팅리스트 조회 함수
	private List<ChatListDTO> selectChatList()  {
		List list = new ArrayList<>();
		return list;
	}
	
	public ChatHandler() {
		super();
		members = new HashMap<>();
	}
	//클라이언트가 소켓에 최초 접속하면 자동으로 실행되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
	}
	//클라이언트가 소켓으로 데이터를 전송하면 실행되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
	}
	//클라이언트가 소켓에서 접속이 끊어지면 자동으로 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		
	}

}
