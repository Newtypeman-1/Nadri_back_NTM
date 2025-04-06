package kr.co.iei.review.model.service;

import java.beans.Transient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.dto.CommDTO;



@Service
public class CommService {
	@Autowired
 private ReviewDao reviewDao;
	public List commList(int reviewNo) {
		List list = reviewDao.commList(reviewNo);
	
		return list;
	}
	@Transactional
	public int deleteComm(int commNo) {
		int result = reviewDao.deleteComm(commNo);
		return result;
	}
	@Transactional
	public int insertComm(CommDTO comm) {
		System.out.println(comm);
int result = reviewDao.insertComm(comm);
		return 0;
	}

}
