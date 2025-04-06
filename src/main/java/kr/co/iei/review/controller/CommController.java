package kr.co.iei.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.review.model.dto.CommDTO;
import kr.co.iei.review.model.service.CommService;



@CrossOrigin("*")
@RestController
@RequestMapping(value="/comm")
public class CommController {
	@Autowired
	private CommService commService;

	@GetMapping(value="/{reviewNo}")
	public ResponseEntity<List> commList(@PathVariable int reviewNo){
	List list = commService.commList(reviewNo);
		 return ResponseEntity.ok(list);
	}
	@DeleteMapping(value="/{commNo}")
	public ResponseEntity<Integer> deleteComm(@PathVariable int commNo){
		int result = commService.deleteComm(commNo);
		 return ResponseEntity.ok(result);
	}
	@PostMapping
	public ResponseEntity<Integer> insertComm(@ModelAttribute CommDTO comm ){
		int result = commService.insertComm(comm);
		return null;
	}
}
