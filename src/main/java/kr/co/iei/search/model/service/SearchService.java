package kr.co.iei.search.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.search.model.dao.SearchDao;
import kr.co.iei.search.model.dto.SearchDTO;

@Service
public class SearchService {
	@Autowired
	private SearchDao searchDao;
	
	public List selectKeyword(SearchDTO search) {
		List keywordList = searchDao.selectKeyword(search); 
		return keywordList;
	}
	@Transactional
	public List searchResult(SearchDTO search) {
		List searchList = searchDao.selectPlaceByKeyword(search);
		if(!searchList.isEmpty()) {
			int result = searchDao.insertSearchLog(search.getQuery());
		}
		return searchList;
	}

}
