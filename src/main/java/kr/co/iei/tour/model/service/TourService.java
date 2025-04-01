package kr.co.iei.tour.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.tour.model.dao.TourDao;

@Service
public class TourService {
	@Autowired
	private TourDao tourDao;
	@Transactional
	public int insertCommon(List list) {
		int result = tourDao.insertCommon(list);
		return 0;
	}

	
	
	
}
