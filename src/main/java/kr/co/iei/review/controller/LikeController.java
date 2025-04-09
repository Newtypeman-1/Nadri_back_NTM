package kr.co.iei.review.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		Map map = likeService.reviewLike(reviewNo);
		return ResponseEntity.ok(map);
	
	}
	@PostMapping
    public ResponseEntity<Integer> addLike(
            @RequestParam("reviewNo") int reviewNo,
            @RequestParam("memberNickname") String memberNickname) {

        int result = likeService.addLike(reviewNo, memberNickname);
        return ResponseEntity.ok(result); // 성공 시 1, 실패 시 0
    }

    // 좋아요 취소
    @DeleteMapping("/{reviewNo}")
    public ResponseEntity<Integer> removeLike(
            @PathVariable int reviewNo,
            @RequestBody Map<String, String> body) {

        String memberNickname = body.get("memberNickname");
        int result = likeService.removeLike(reviewNo, memberNickname);
        return ResponseEntity.ok(result); // 성공 시 1, 실패 시 0
    }

}
