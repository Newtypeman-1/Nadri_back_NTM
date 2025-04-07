package kr.co.iei.plan.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.plan.model.dao.PlanDao;
import kr.co.iei.plan.model.dto.PlanDTO;
import kr.co.iei.util.JwtUtils;

@Service
public class PlanService {
	@Autowired
	private PlanDao planDao;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public PlanDTO verifyPlan(String refreshToken, int planNo) {
		LoginMemberDTO loginMember = jwtUtils.checkToken(refreshToken); 
		PlanDTO plan = planDao.verifyPlan(loginMember.getMemberEmail(), planNo);
		return plan;
	}

	public List selectNearby(double lat, double lng, int radius) {
		List list = planDao.selectNearby(lat, lng, radius);
		return list;
	}

	public List selectPlanItineraries(int planNo) {
		List list = planDao.selectPlanItineraries(planNo);
		return list;
	}

	public boolean isPlanOwner(String refreshToken, int planNo) {
		LoginMemberDTO loginMember = jwtUtils.checkToken(refreshToken);
		int count = planDao.isPlanOwner(loginMember.getMemberEmail(), planNo);
		return count > 0;
	}

}
