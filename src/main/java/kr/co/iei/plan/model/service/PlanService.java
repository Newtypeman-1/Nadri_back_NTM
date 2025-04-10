package kr.co.iei.plan.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.plan.model.dao.PlanDao;
import kr.co.iei.plan.model.dto.ItineraryDTO;
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

	@Transactional
	public boolean insertPlan(PlanDTO plan, List<ItineraryDTO> list) {
		if(planDao.insertTripPlan(plan) != 1) return false;
		for(ItineraryDTO i : list) {
			i.setPlanNo(plan.getPlanNo());
			if(planDao.insertTripItinerary(i) != 1) return false;
		}
		return true;
	}
	public AdminStatsDTO selectPlanStats() {
		AdminStatsDTO planStats = planDao.selectPlanStats();
		return planStats;
	}

	public AdminStatsDTO selectMostPlace() {
		AdminStatsDTO mostPlace = planDao.selectMostPlace();
		return mostPlace;
	}

}
