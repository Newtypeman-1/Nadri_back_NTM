package kr.co.iei.search.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.place.model.dto.CategoryDTO;
import kr.co.iei.search.model.dto.QueryDTO;
import kr.co.iei.search.model.dto.SearchLogDTO;
import kr.co.iei.search.model.service.SearchService;
@CrossOrigin("*")
@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	public SearchService searchService;
	
	@GetMapping("/keyword")
	public ResponseEntity<List> selectKeyword(@RequestParam String query, @RequestParam(required = false) String[] type){
		QueryDTO search = new QueryDTO(query, type);
		List<CategoryDTO> keywordList = searchService.selectKeyword(search);
		return ResponseEntity.ok(keywordList);
	}
	@GetMapping
	public ResponseEntity<Map> searchResult(@RequestParam String query,@RequestParam String[] type){
		QueryDTO search = new QueryDTO(query, type);
		Map searchResult = searchService.searchResult(search);
		return ResponseEntity.ok(searchResult);
	}
	@GetMapping("/place")
	public ResponseEntity<List> placeTitle(@RequestParam String query){
		List titleList = searchService.searchPlaceTitle(query);
		return ResponseEntity.ok(titleList);
	}
	@GetMapping("/popular")
	public ResponseEntity<Map> mostSearch(@RequestParam String date){
		Map<String,List<SearchLogDTO>> popular = searchService.selectMostSearch(date);
		return ResponseEntity.ok(popular);
	}
}
