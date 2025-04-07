package kr.co.iei.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.review.model.dto.ReportService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/report")
public class ReportController {
	@Autowired
	private ReportService reportService;
	@PostMapping
	public ResponseEntity<Integer> insertreport(@RequestParam("reviewNo") int reviewNo,
			@RequestParam("memberNickname") String memberNickname,
			@RequestParam("reportReason") String reportReason ){
		System.out.println(reviewNo);
		System.out.println(memberNickname);
		System.out.println(reportReason);
		 return null;
	}
}
