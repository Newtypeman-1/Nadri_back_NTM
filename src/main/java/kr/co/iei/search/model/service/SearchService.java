package kr.co.iei.search.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List searchResult(SearchDTO search) {
		List searchList = searchDao.searchResult(search);
		return searchList;
	}

}
