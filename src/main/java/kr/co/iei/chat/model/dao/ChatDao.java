package kr.co.iei.chat.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.chat.model.dto.ChatContent;

@Mapper
public interface ChatDao {

	List selectGroupList(String memberNickname);

	List selectRoomData(ArrayList<Integer> chatList);

	List selectChatContent(int chatNo);

	int insertText(ChatContent cc);

}
