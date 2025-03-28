package kr.co.iei.chat.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDao {

	List selectGroupList(String memberNickname);

	List selectRoomData(ArrayList<Integer> chatList);

}
