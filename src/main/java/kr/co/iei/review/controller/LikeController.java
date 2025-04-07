package kr.co.iei.review.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.review.model.dto.LikeDTO;
import kr.co.iei.review.model.service.LikeService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/likes")
public class LikeController {
	@Autowired
	private LikeService likeService;
	
	@GetMapping(value="/{reviewNo}")
	public ResponseEntity<Map> reviewLike(@PathVariable int reviewNo){
		System.out.println(reviewNo);
		Map map = likeService.reviewLike(reviewNo);
		return ResponseEntity.ok(map);
	
	}

}
