package kr.co.iei.chat.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.chat.model.dao.ChatDao;
import kr.co.iei.chat.model.dto.ChatContent;

@Service
public class ChatService {
	@Autowired
	private ChatDao chatDao;

	public List selectRoomList(String memberNickname) {
		List chatList = chatDao.selectRoomList(memberNickname);
		return chatList;
	}

	public List selectRoomData(String memberNickname) {
		List roomDataList = chatDao.selectRoomData(memberNickname);
		return roomDataList;
	}

	public List selectChatContent(int chatNo) {
		List chatContent = chatDao.selectChatContent(chatNo);
		return chatContent;
	}
	@Transactional
	public int insertText(ChatContent cc) {
		int result = chatDao.insertText(cc);
		return result;
	}

	public Set selectGroupSet(int chatNo) {
		Set groupSet = new HashSet<>(chatDao.selectGroupSet(chatNo)) ;
		return groupSet;
	}
	
}
