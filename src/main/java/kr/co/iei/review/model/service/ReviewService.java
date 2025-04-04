package kr.co.iei.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.util.PageInfo;
import kr.co.iei.util.PageInfoUtil;


@Service
public class ReviewService {
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private PageInfoUtil pageInfoUtil;
	public Map reviewList(int reqPage, String value) {
		int numPerPage =9;
		 int  pageNaviSize=5;
		 HashMap<String, Object> map = new HashMap<>();
		 map.put("value",value);
		 int totalCount=reviewDao.totalCount(map);
		 System.out.println(totalCount);
		PageInfo pi =  pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		map.put("pi", pi);
		List list = reviewDao.selectBoardList(map);
		System.out.println(list);
		map.put("list",list);
		return map;
	}

}
