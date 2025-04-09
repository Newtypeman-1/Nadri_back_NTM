package kr.co.iei.review.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.review.model.dto.ReviewDTO;
import kr.co.iei.review.model.service.ReviewService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@GetMapping
	public ResponseEntity<Map> reviewlist(@RequestParam int reqPage, @RequestParam String value){
	
		Map map = reviewService.reviewList(reqPage,value);
		return ResponseEntity.ok(map);
	}
	@GetMapping(value="/{reviewNo}")
	public ResponseEntity<ReviewDTO>selectOneReview(@PathVariable int reviewNo){
	
		ReviewDTO review =reviewService.selectOneReview(reviewNo);
		return ResponseEntity.ok(review);
	}
	@DeleteMapping(value="/{reviewNo}")
	public ResponseEntity<Integer>deleteReview(@PathVariable int reviewNo){
		
		int result = reviewService.deleteReview(reviewNo);
		return ResponseEntity.ok(result);
	}
		
	@GetMapping(value="/detail/{placeId}")
	public ResponseEntity<List> oneReviewlist(@PathVariable int placeId){
	
	System.out.println(placeId);
		List list = reviewService.oneReviewList(placeId);
		return ResponseEntity.ok(list);
	}
}
