package kr.co.iei.chat.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import kr.co.iei.chat.model.dto.ChatContentDTO;
import kr.co.iei.chat.model.dto.ChatRoomDTO;

@Mapper
public interface ChatDao {

	List selectRoomList(String memberNickname);

	List selectRoomData(String memberNickname);

	List selectChatContent(int chatNo);

	int insertText(ChatContentDTO cc);

	Set selectGroupSet(int chatNo);

	int createRoom(ChatContentDTO cc);

	int insertGroup(ChatContentDTO cc);

	int leaveGroup(ChatContentDTO cc);

	int checkGroup(int chatNo);

	int deleteChat(int chatNo);

	void insertLeaveMsg(ChatContentDTO cc);

	void insertInviteMsg(ChatContentDTO cc);

	int updateTitle(ChatRoomDTO crd);

	int selectLatestChatContentNo(int chatNo);

	void updateReadStatus(ChatRoomDTO crd);

	
}
