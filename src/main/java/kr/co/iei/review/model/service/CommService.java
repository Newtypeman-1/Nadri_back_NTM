package kr.co.iei.review.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.iei.review.model.dao.ReviewDao;



@Service
public class CommService {
	@Autowired
 private ReviewDao reviewDao;
	public List commList(int reviewNo) {
		List list = reviewDao.commList(reviewNo);
	
		return list;
	}
	public int deleteComm(int commNo) {
		int result = reviewDao.deleteComm(commNo);
		return result;
	}
}
