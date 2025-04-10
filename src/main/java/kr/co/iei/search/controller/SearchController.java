package kr.co.iei.search.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.search.model.dto.SearchDTO;
import kr.co.iei.search.model.service.SearchService;
@CrossOrigin("*")
@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	public SearchService searchService;
	
	@GetMapping("/keyword")
	public ResponseEntity<List> selectKeyword(@RequestParam String query, @RequestParam(required = false) String[] type){
		SearchDTO search = new SearchDTO(query, type);
		List keywordList = searchService.selectKeyword(search);
		return ResponseEntity.ok(keywordList);
	}
	@GetMapping
	public ResponseEntity<List> searchResult(@RequestParam String query,@RequestParam String[] type){
		SearchDTO search = new SearchDTO(query, type);
		List searchList = searchService.searchResult(search);
		return ResponseEntity.ok(searchList);
	}
}
