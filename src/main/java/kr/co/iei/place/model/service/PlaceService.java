package kr.co.iei.place.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.security.Jwks.HASH;
import kr.co.iei.place.model.dao.PlaceDao;
import kr.co.iei.util.PageInfo;
import kr.co.iei.place.model.dto.CategoryDTO;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.SpotDTO;
import kr.co.iei.search.model.dto.SearchLogDTO;
import kr.co.iei.util.PageInfoUtil;

@Service
public class PlaceService {
	@Autowired
	private PlaceDao placeDao;
	@Autowired
	private PageInfoUtil pageInfoUtil;

//	@Transactional
//	public int insertCommon(List list) {
//		int result = placeDao.insertCommon(list);
//		return 0;
//	}

	// placeInfo 조회(필터없는 전체조회)

	public Map selectPlaceList(int reqPage, int placeTypeId) {
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = placeDao.totalCount(placeTypeId);
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);

		Map<String, Object> map = new HashMap<>();
		map.put("placeTypeId", placeTypeId);
		map.put("pi", pi);

		List<PlaceInfoDTO> list = placeDao.selectPlaceList(map);
		// placeTitle 괄호제거
		for (PlaceInfoDTO place : list) {
			String title = place.getPlaceTitle();
			if (title != null) {
				String cleanedTitle = title.replaceAll("\\(.*?\\)", "").trim();
				place.setPlaceTitle(cleanedTitle);
			}
		}

		Map<String, Object> map2 = new HashMap<>();
		map.put("list", list);
		map.put("pi", pi);
		map.put("totalCount", totalCount);

		return map;
	}

	public void insertPlace() {
		// TODO Auto-generated method stub

	}

	public List<CategoryDTO> selectPlaceType() {
		List<CategoryDTO> placeType = placeDao.selectPlaceType();
		return placeType;
	}

	public PlaceInfoDTO selectOnePlace(int placeId) {
		PlaceInfoDTO place = placeDao.selectOnePlace(placeId);
		String title = place.getPlaceTitle();
		if (title != null) {
			String cleanedTitle = title.replaceAll("\\(.*?\\)", "").trim();
			place.setPlaceTitle(cleanedTitle);
		}
		return place;
	}
	
	@Transactional
	public List<Map<String, Object>> getBookmarkStatusList(String memberNickname, List<Integer> placeIds) {
		 Map<String, Object> paramMap = new HashMap<>();
		    paramMap.put("memberNickname", memberNickname);
		    paramMap.put("placeId", placeIds);
		return placeDao.selectBookmarkStatusList(paramMap);
	}
	@Transactional
	public boolean toggleBookmark(String memberNickname, int placeId) {
	    Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("memberNickname", memberNickname);
	    paramMap.put("placeId", placeId);

	    // 리스트 조회 방식 기준: row 존재 여부로 판단
	    int count = placeDao.checkBookmark(paramMap);
	    if (count > 0) {
	        placeDao.deleteBookmark(paramMap);
	        return false; // 해제됨
	    } else {
	        placeDao.insertBookmark(paramMap);
	        return true; // 등록됨
	    }
	}

	public Map<String, List<CategoryDTO>> selectPlaceCategory() {
		List<CategoryDTO> allList = placeDao.selectAllPlaceCategories();
		Map<String, List<CategoryDTO>> category = new HashMap<>();
		for (CategoryDTO dto : allList) {
			int index = dto.getCatIndex(); 
			String key = "cat" + index; 

			if (!category.containsKey(key)) {
				category.put(key, new ArrayList<>());
			}
			category.get(key).add(dto);
		}
		return category;
	}

	public List<CategoryDTO> selectPlaceArea() {
		List<CategoryDTO> area = placeDao.selectArea();
		return area;
	}

}
