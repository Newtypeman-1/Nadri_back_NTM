package kr.co.iei.member.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.place.model.dto.PlaceInfoDTO;

@Mapper
public interface MypageDao {

	List reviewsList(HashMap<String, Object> map);

	List<PlaceInfoDTO> BookmarkList(Map<String, Object> map);

	List plannerList(HashMap<String, Object> map);

}
