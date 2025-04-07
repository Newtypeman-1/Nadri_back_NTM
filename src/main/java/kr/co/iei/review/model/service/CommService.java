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
	public CommDTO insertComm(CommDTO comm) {
	
int result = reviewDao.insertComm(comm);

int commNo= comm.getCommNo();
System.out.println(commNo);
CommDTO comment = reviewDao.currentComm(commNo);
		return comment;
	}

}
