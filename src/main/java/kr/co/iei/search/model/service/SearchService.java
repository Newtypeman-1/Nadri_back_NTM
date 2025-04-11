package kr.co.iei.search.model.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.iei.search.model.dao.SearchDao;
import kr.co.iei.search.model.dto.QueryDTO;
import kr.co.iei.search.model.dto.SearchLogDTO;

@Service
public class SearchService {
	@Autowired
	private SearchDao searchDao;
	
	public List selectKeyword(QueryDTO search) {
		List keywordList = searchDao.selectKeyword(search); 
		return keywordList;
	}


	@Transactional
	public List searchResult(QueryDTO search) {
		String query= search.getQuery();
		List searchList = searchDao.selectPlaceByKeyword(search);
		if(!searchList.isEmpty()&&!query.equals("")) {
			int result = searchDao.insertSearchLog(query);
		}
		return searchList;
	}


	public Map<String, List<SearchLogDTO>> selectMostSearch(String date) {
		Map<String, List<SearchLogDTO>> popular = new HashMap<>();
		 // 문자열 → LocalDate로 변환
	    LocalDate today = LocalDate.parse(date); // "2025-04-10"
	    // 주간 (월~일)
	    LocalDate weekStart = today.with(DayOfWeek.MONDAY);
	    LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);
	    // 월 시작/끝
	    LocalDate monthStart = today.withDayOfMonth(1);
	    LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());
	    // 연도 시작/끝
	    LocalDate yearStart = today.withDayOfYear(1);
	    LocalDate yearEnd = today.withDayOfYear(today.lengthOfYear());
	    // DAO 호출 시 문자열 형식으로 다시 변환
	    popular.put("daily", searchDao.selectPopularByDate(date));
	    popular.put("weekly", searchDao.selectPopularByWeek(
	            weekStart.toString(), weekEnd.toString()));
	    popular.put("monthly", searchDao.selectPopularByMonth(
	            monthStart.toString(), monthEnd.toString()));
	    popular.put("yearly", searchDao.selectPopularByYear(
	            yearStart.toString(), yearEnd.toString()));

	    return popular;
	}

}
