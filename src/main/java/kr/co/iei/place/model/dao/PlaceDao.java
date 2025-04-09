package kr.co.iei.place.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.SpotDTO;
import kr.co.iei.util.PageInfo;

@Mapper
public interface PlaceDao {

//	int insertCommon(List list);

	int totalCount();

	List<PlaceInfoDTO> selectPlaceList(Map<String, Object> map);
	
	
	//SPOT관련 PLACE_DETAIL 및 SPOT_DETATIL추가
	List<PlaceInfoDTO> selectSpotDetail();//PlaceDataApi에서

	//
	void updateDetailInfo(PlaceInfoDTO place);
	void insertSpotInfo(SpotDTO spot);
	List selectSpotList(PageInfo pi);
	
	//DB초기 세팅
	void insertPlaceInfoList(List<PlaceInfoDTO> list);

	

	List selectPlaceType();





	
}
