package kr.co.iei.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.dto.CommDTO;
import kr.co.iei.review.model.dto.LikeDTO;
import kr.co.iei.review.model.dto.PlaceImgDTO;
import kr.co.iei.review.model.dto.ReportDTO;
import kr.co.iei.review.model.dto.ReviewDTO;
import kr.co.iei.util.PageInfo;
import kr.co.iei.util.PageInfoUtil;

@Service
public class ReviewService {
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private PageInfoUtil pageInfoUtil;

	public Map reviewList(int reqPage, String type, int[] id) {
		int numPerPage = 9;
		int pageNaviSize = 5;
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> result = new HashMap<>();
		map.put("type", type);
		int totalCount = reviewDao.totalCount(map);
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);
		map.put("pi", pi);
		map.put("id",id);
		List list = reviewDao.selectBoardList(map);
		result.put("list", list);
		result.put("pi", pi);
		return result;
	}

	public ReviewDTO selectOneReview(int reviewNo) {
		ReviewDTO review = reviewDao.selectOneReview(reviewNo);
		return review;
	}

	public int deleteReview(int reviewNo) {
		int result = reviewDao.deleteReview(reviewNo);
		return result;
	}

	public List<AdminStatsDTO> selectReviewStats() {
		List<AdminStatsDTO> reviewStats = reviewDao.selectReviewStats();
		return reviewStats;
	}

	public List<ReviewDTO> selectHotReview(int type) {
		List<ReviewDTO> hotReviews = reviewDao.selectHotReview(type);
		return hotReviews;
	}

	public List selectReportedReview(int status) {
		List<ReviewDTO> reportedReviews = reviewDao.selectReportedReview(status);
		return reportedReviews;
	}

	public List oneReviewList(int placeId) {
		List list = reviewDao.selectOneBoardList(placeId);
		return list;
	}

	@Transactional
	public int insertReview(ReviewDTO review, List<PlaceImgDTO> placeImgList) {
		// TODO Auto-generated method stub
		int result = reviewDao.insertReview(review);
		for (PlaceImgDTO placeImg : placeImgList) {
			placeImg.setPlaceId(review.getPlaceId());
			placeImg.setReviewNo(review.getReviewNo());
			System.out.println(placeImg);
			result += reviewDao.insertPlaceImg(placeImg);
		}
		return result;
	}

	public List searchImg(int reviewNo) {
		List list = reviewDao.searchImg(reviewNo);
		return list;
	}

	@Transactional
	public int updateReview(ReviewDTO reviewDTO) {
		int result = reviewDao.updateReview(reviewDTO);
		return result;
	}

	public Map reviewLike(int reviewNo) {
		List likeMember = reviewDao.reviewLike(reviewNo);
		int likes = reviewDao.reviewCount(reviewNo);
		HashMap<String, Object> map = new HashMap<>();
		map.put("likes", likes);
		map.put("likeMember", likeMember);
		return map;
	}

	@Transactional
	public int addLike(int reviewNo, String memberNickname) {
		int result = reviewDao.insertLike(reviewNo, memberNickname);
		return result;
	}

	@Transactional
	public int removeLike(int reviewNo, String memberNickname) {
		int result = reviewDao.deleteLike(reviewNo, memberNickname);
		return result;
	}

	@Transactional
	public int insertReport(ReportDTO reportDTO) {
		int result = reviewDao.insertReport(reportDTO);
		return result;
	}

	public List reportList(int reviewNo) {
		List list = reviewDao.reportList(reviewNo);
		return list;
	}

	public List commList(int reviewNo) {
		List list = reviewDao.commList(reviewNo);
		return list;
	}

	@Transactional
	public int deleteComm(int commNo) {
		int result = reviewDao.deleteComm(commNo);
		return result;
	}

	@Transactional
	public CommDTO insertComm(CommDTO comm) {
		int result = reviewDao.insertComm(comm);
		int commNo = comm.getCommNo();
		CommDTO comment = reviewDao.currentComm(commNo);
		return comment;
	}

	@Transactional
	public int patchComment(CommDTO commDTO) {
		int result = reviewDao.patchComment(commDTO);
		return result;
	}

}
