package kr.co.iei.place.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.place.model.dao.PlaceDao;
import kr.co.iei.util.PageInfo;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.SpotDTO;
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
	
	//placeInfo 조회(필터없는 전체조회)
	public Map selectPlaceList(int reqPage, int placeCat) {
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = placeDao.totalCount();
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		
		Map<String, Object> map = new HashMap<>();
		map.put("placeCat", placeCat);
		map.put("pi", pi);
		
		List<PlaceInfoDTO> list = placeDao.selectPlaceList(map);
		  //placeTitle 괄호제거
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
	
	//SpotList조회
	public Map selectSpotList(int reqPage) {
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = placeDao.totalCount();
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		
		List<SpotDTO> list = placeDao.selectSpotList(pi);
		  for (SpotDTO place : list) {
	            String title = place.getPlaceTitle();
	            if (title != null) {
	                String cleanedTitle = title.replaceAll("\\(.*?\\)", "").trim();
	                place.setPlaceTitle(cleanedTitle);
	            }
	        }
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pi", pi);
		map.put("totalCount", totalCount);
		return map;
	}

	public List selectPlaceType() {
		List placeTypeList = placeDao.selectPlaceType();
		return placeTypeList;
	}
	
	
	
}
