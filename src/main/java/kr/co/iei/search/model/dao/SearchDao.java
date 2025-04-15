package kr.co.iei.search.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.search.model.dto.QueryDTO;
import kr.co.iei.search.model.dto.SearchLogDTO;

@Mapper
public interface SearchDao {

	List selectKeyword(QueryDTO search);

	List selectPlaceByKeyword(QueryDTO search);

	int insertSearchLog(String query);

	List<SearchLogDTO> selectPopularByDate(String date);

	List<SearchLogDTO> selectPopularByWeek(String startDate, String endDate);

	List<SearchLogDTO> selectPopularByMonth(String startDate, String endDate);

	List<SearchLogDTO> selectPopularByYear(String startDate, String endDate);

	List searchPlaceTitle(String query);

	int[] selectPlanByPlace(List<Integer> placeList);

	int[] selectReviewByPlace(List<Integer> placeList);



	

}
