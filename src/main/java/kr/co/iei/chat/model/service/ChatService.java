package kr.co.iei.chat.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.chat.model.dao.ChatDao;
import kr.co.iei.chat.model.dto.ChatContentDTO;
import kr.co.iei.chat.model.dto.ChatMemberDTO;
import kr.co.iei.chat.model.dto.ChatRoomDTO;

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
		for(ChatRoomDTO r : (ArrayList<ChatRoomDTO>)roomDataList) {
			List<ChatMemberDTO> groupInfo  = chatDao.selectGroupInfo(r.getChatNo());
			r.setGroupInfo(groupInfo);
		}
		return roomDataList;
	}

	public List selectChatContent(int chatNo) {
		List chatContent = chatDao.selectChatContent(chatNo);
		return chatContent;
	}
	@Transactional
	public int insertText(ChatContentDTO cc) {
		int result = chatDao.insertText(cc);
		return result;
	}

	public Set selectGroupSet(int chatNo) {
		Set groupSet = new HashSet<>(chatDao.selectGroupSet(chatNo)) ;
		return groupSet;
	}
	@Transactional
	public int createRoom(ChatContentDTO cc) {
		int result = chatDao.createRoom(cc);
		if(result>0) {
			result += chatDao.insertGroup(cc);
			//트리거로 최초 채팅 넣기
		}
		return result;
	}
	@Transactional
	public int inviteRoom(ChatContentDTO cc) {
		int result = chatDao.insertGroup(cc);
		if(result>0) {
			chatDao.insertInviteMsg(cc);
		}
		return result;
	}
	@Transactional
	public int leaveRoom(ChatContentDTO cc) {
		int result = chatDao.leaveGroup(cc);
		if(result>0) {
			//채팅방에 남은 멤버가 없으면 채팅방 삭제 로직
			int numOfGroup = chatDao.checkGroup(cc.getChatNo());
			if(numOfGroup==0) {
				result += chatDao.deleteChat(cc.getChatNo());
			}else {
				chatDao.insertLeaveMsg(cc);
			}
		}
		return result;
	}
	@Transactional
	public int updateTitle(ChatRoomDTO crd) {
		int result = chatDao.updateTitle(crd);
		return result;
	}

	public ChatRoomDTO selectLatestChatInfo(int chatNo) {
		ChatRoomDTO room = chatDao.selectLatestChatContentInfo(chatNo);
		return room;
	}
	@Transactional
	public void updateReadStatus(ChatRoomDTO crd) {
		chatDao.updateReadStatus(crd);
	}


	
}
