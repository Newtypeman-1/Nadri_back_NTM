package kr.co.iei.place.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.SpotDTO;
import kr.co.iei.util.PageInfo;

@Mapper
public interface PlaceDao {

	int totalCount(int placeTypeId);
	int totalCount();

	//플레이스리스트조회
	List<PlaceInfoDTO> selectPlaceList(Map<String, Object> map);

	List selectPlaceType();

	PlaceInfoDTO selectOnePlace(int placeId);
	
	
	//DB초기 세팅
	void insertPlaceInfoList(List<PlaceInfoDTO> list);
	//오버뷰 인서트
	List<PlaceInfoDTO> selectPlaces();
	void updateOverview(PlaceInfoDTO place);

	
}
