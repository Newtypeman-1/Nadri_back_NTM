package kr.co.iei.review.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.review.model.dao.CommDao;



@Service
public class CommService {
	@Autowired
 private CommDao commDao;
}
