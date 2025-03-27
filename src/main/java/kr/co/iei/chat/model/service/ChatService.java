package kr.co.iei.chat.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.chat.model.dao.ChatDao;

@Service
public class ChatService {
	@Autowired
	private ChatDao chatDao;

	public List selectGroupList(String memberNickname) {
		List groupList = chatDao.selectGroupList(memberNickname);
		return groupList;
	}
	
}
