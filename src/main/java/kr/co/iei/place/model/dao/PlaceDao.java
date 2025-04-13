package kr.co.iei.place.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.iei.place.model.dto.CategoryDTO;
import kr.co.iei.place.model.dto.FilterMapRow;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
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

	//코드조회 쿼리
	List<FilterMapRow> findFilterMapRows(List<String> filters, int selectedMenu);

	int getFilteredPlaceCount(List<Integer> placeTypeIds, List<String> cat2Codes, List<String> cat3Codes);

	List<PlaceInfoDTO> selectPlaceListByFilterPaged(List<Integer> placeTypeIds, List<String> cat2Codes,
			List<String> cat3Codes, int order, int start, int end, String memberNickname);




}
