package kr.co.iei.plan.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.plan.model.dao.PlanDao;

@Service
public class PlanService {
	@Autowired
	private PlanDao planDao;
	
	public List selectNearby(double lat, double lng, int radius) {
		List list = planDao.selectNearby(lat, lng, radius);
		return list;
	}

}
