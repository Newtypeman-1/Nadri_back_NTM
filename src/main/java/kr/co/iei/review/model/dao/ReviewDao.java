package kr.co.iei.review.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.review.model.dto.CommDTO;
import kr.co.iei.review.model.dto.LikeDTO;
import kr.co.iei.review.model.dto.ReportDTO;
import kr.co.iei.review.model.dto.ReviewDTO;
import kr.co.iei.util.PageInfo;

@Mapper
public interface ReviewDao {

	int totalCount(HashMap<String, Object> map);

	List selectBoardList(HashMap<String, Object> map);

	int reviewTotalCount();

	List allBoardList(PageInfo pi);

	ReviewDTO selectOneReview(int reviewNo);


	List commList(int reviewNo);

	int deleteComm(int commNo);

	int insertComm(CommDTO comm);

	CommDTO currentComm(int commNo);

	int deleteReview(int reviewNo);

	LikeDTO reviewLike(int reviewNo);

	int reviewCount(int reviewNo);

	int insertLike(int reviewNo, String memberNickname);

	int deleteLike(int reviewNo, String memberNickname);

	int insertReport(ReportDTO reportDTO);

	


}
