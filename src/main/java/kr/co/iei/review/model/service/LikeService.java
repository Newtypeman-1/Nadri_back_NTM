package kr.co.iei.review.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.dto.LikeDTO;

@Service
public class LikeService {
	@Autowired
	private ReviewDao reviewDao;

	public Map reviewLike(int reviewNo) {
		LikeDTO likeMember = reviewDao.reviewLike(reviewNo);
		int likes =reviewDao.reviewCount(reviewNo);
		 HashMap<String, Object> map = new HashMap<>();
		 map.put("likes",likes);
		 map.put("likeMember", likeMember);
		return map;
	}

	
}
