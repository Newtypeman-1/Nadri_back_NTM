package kr.co.iei.place.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.iei.place.model.dto.CategoryDTO;
import kr.co.iei.place.model.dto.PlaceFilterRequest;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.review.model.dto.PlaceImgDTO;
import kr.co.iei.util.PageInfo;

@Mapper
public interface PlaceDao {

	int totalCount();
	
	int totalCount(int selectedMenu);

	//플레이스 리스트 조회
	List<PlaceInfoDTO> selectALLPlaceList(Map<String, Object> map);

	//플레이스 타입 아이디 조회
	List<CategoryDTO> selectPlaceType();

	//플레이스 하나 조회(상세보기)
	PlaceInfoDTO selectPlaceWithBookmarked(int placeId, String memberNickname);
	
	List<CategoryDTO> selectAllPlaceCategories();

	List<CategoryDTO> selectArea();

	//즐겨찾기 상태 조회
	int checkBookmark(String memberNickname, int placeId);
	
	void deleteBookmark(String memberNickname, int placeId);

	void insertBookmark(String memberNickname, int placeId);

	// DB초기 세팅
	void insertPlaceInfoList(List<PlaceInfoDTO> list);

	// 오버뷰 인서트
	List<PlaceInfoDTO> selectPlaces();

	void updateOverview(PlaceInfoDTO place);

	//필터적용된 토탈카운트 조회
	int getFilteredPlaceCount(PlaceFilterRequest request);

	//필터적용 리스트 조회
	List<PlaceInfoDTO> selectPlaceListByFilterPaged(PlaceFilterRequest request, PageInfo pi);

	//조회수 저장 및 가져오기
	int updatePlaceViewCount(int placeId);
	int selectViewCount(int placeId);

	List<PlaceImgDTO> selectImagesByPlaceId(int placeId);

	void deleteByImageNo(int placeImageNo);

	int updatePlace(PlaceInfoDTO placeInfoDTO);




	
	
}
