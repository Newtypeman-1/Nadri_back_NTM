package kr.co.iei.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.review.model.service.CommService;



@CrossOrigin("*")
@RestController
@RequestMapping(value="/comm")
public class CommController {
	@Autowired
	private CommService commService;

}
