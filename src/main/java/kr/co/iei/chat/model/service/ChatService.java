package kr.co.iei.chat.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.chat.model.dao.ChatDao;

@Service
public class ChatService {
	@Autowired
	private ChatDao chatDao;

	public List selectGroupList(String memberNickname) {
		List chatList = chatDao.selectGroupList(memberNickname);
		return chatList;
	}

	public List selectRoomData(ArrayList<Integer> chatList) {
		List roomDataList = chatDao.selectRoomData(chatList);
		return roomDataList;
	}

	public List selectChatContent(int chatNo) {
		List chatContent = chatDao.selectChatContent(chatNo);
		return chatContent;
	}
	
}
