package kr.co.iei.tour.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.tour.model.dao.TourDao;
import kr.co.iei.util.PageInfo;
import kr.co.iei.util.PageInfoUtil;

@Service
public class TourService {
	@Autowired
	private TourDao tourDao;
	@Autowired
	private PageInfoUtil pageInfoUtil;
	
	@Transactional
	public int insertCommon(List list) {
		int result = tourDao.insertCommon(list);
		return 0;
	}
	public Map selectTourList(int reqPage) {
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = tourDao.totalCount();
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		
		List list = tourDao.selectTourList(pi);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pi", pi);
		
		return map;
	}
	
	
	
}
