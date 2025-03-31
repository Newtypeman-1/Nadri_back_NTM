package kr.co.iei.chat.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.chat.model.dto.ChatContent;

@Mapper
public interface ChatDao {

	List selectRoomList(String memberNickname);

	List selectRoomData(String memberNickname);

	List selectChatContent(int chatNo);

	int insertText(ChatContent cc);

	Set selectGroupSet(int chatNo);

	int createRoom(ChatContent cc);

	int createGroup(ChatContent cc);

}
