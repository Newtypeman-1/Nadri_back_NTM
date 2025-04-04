package kr.co.iei.place.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.place.model.dao.PlaceDao;
import kr.co.iei.util.PageInfo;
import kr.co.iei.util.PageInfoUtil;

@Service
public class PlaceService {
	@Autowired
	private PlaceDao placeDao;
	@Autowired
	private PageInfoUtil pageInfoUtil;
	
	@Transactional
	public int insertCommon(List list) {
		int result = placeDao.insertCommon(list);
		return 0;
	}
	public Map selectPlaceList(int reqPage) {
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = placeDao.totalCount();
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		
		List list = placeDao.selectPlaceList(pi);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pi", pi);
		
		return map;
	}
	public void insertPlace() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
