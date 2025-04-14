package kr.co.iei.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.plan.model.dao.PlanDao;
import kr.co.iei.plan.model.dto.ItineraryDTO;
import kr.co.iei.plan.model.dto.ItineraryWithPlaceDTO;
import kr.co.iei.plan.model.dto.PlanDTO;
import kr.co.iei.util.JwtUtils;
import kr.co.iei.util.PageInfo;
import kr.co.iei.util.PageInfoUtil;

@Service
public class PlanService {
	@Autowired
	private PlanDao planDao;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PageInfoUtil pageInfoUtil;

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
		List<ItineraryWithPlaceDTO> list = planDao.selectPlanItinerariesWithPlace(planNo);
		return list;
	}

	public boolean isPlanOwner(String refreshToken, int planNo) {
		LoginMemberDTO loginMember = jwtUtils.checkToken(refreshToken);
		int count = planDao.isPlanOwner(loginMember.getMemberEmail(), planNo);
		return count > 0;
	}

	@Transactional
	public boolean insertPlan(PlanDTO plan, List<ItineraryDTO> list) {
		if (planDao.insertTripPlan(plan) != 1)
			return false;
		for (ItineraryDTO i : list) {
			i.setPlanNo(plan.getPlanNo());
			if (planDao.insertTripItinerary(i) != 1)
				return false;
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

	public PlanDTO selectOnePlan(Integer planNo) {
		PlanDTO plan = planDao.selectOnePlan(planNo);
		return plan;
	}

	@Transactional
	public boolean updatePlan(PlanDTO plan, List<ItineraryDTO> list) {
		if(planDao.updatePlan(plan) != 1) return false;
		if(planDao.deleteItineraries(plan.getPlanNo()) < 1) return false;
		for (ItineraryDTO i : list) {
			i.setPlanNo(plan.getPlanNo());
			if (planDao.insertTripItinerary(i) != 1)
				return false;
		}
		return true;
	}

	public Map<String, Object> selectPagedNearby(double lat, double lng, double width, double height, int page, int size, int sortOption,
			Integer filterOption) {
		// 시작 인덱스 계산
		int start = (page - 1) * size;

		// 쿼리용 파라미터 구성
		Map<String, Object> map = new HashMap<>();
		map.put("lat", lat);
		map.put("lng", lng);
		map.put("width", width);
		map.put("height", height);
		map.put("start", start);
		map.put("size", size);
		map.put("sortOption", sortOption);
		map.put("filterOption", filterOption);

		// 데이터 조회
		List list = planDao.selectPagedNearby(map);
		int totalCount = planDao.countNearby(map);

		// PageInfo 계산
		PageInfo pi = pageInfoUtil.getPageInfo(page, size, 5, totalCount); // 5는 네비게이션 버튼 개수

		// 결과 구성
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("totalCount", totalCount);
		result.put("pageInfo", pi); // 전체 페이징 정보 제공

		return result;
	}

}
