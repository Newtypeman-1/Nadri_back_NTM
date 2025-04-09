package kr.co.iei.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.dto.ReviewDTO;
import kr.co.iei.review.model.dto.ReviewStatsDTO;
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
		PageInfo pi =  pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		map.put("pi", pi);
		List list = reviewDao.selectBoardList(map);
		map.put("list",list);
		return map;
	}
	public ReviewDTO selectOneReview(int reviewNo) {
		ReviewDTO  review = reviewDao.selectOneReview(reviewNo);
		
		return review;
	}
	public int deleteReview(int reviewNo) {
		int result = reviewDao.deleteReview(reviewNo);
		return result;
	}
	public List<ReviewStatsDTO> selectReviewStats() {
		List<ReviewStatsDTO> reviewStats = reviewDao.selectReviewStats();
		return reviewStats;
	}
	public List<ReviewDTO> selectHotReview(int type) {
		List<ReviewDTO> hotReviews = reviewDao.selectHotReview(type);
		return hotReviews;
	}
	public List selectReportedReview() {
		List<ReviewDTO> reportedReviews = reviewDao.selectReportedReview();
		return reportedReviews;
	}

}
