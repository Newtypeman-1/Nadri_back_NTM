package kr.co.iei.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.plan.model.dao.PlanDao;
import kr.co.iei.plan.model.dto.ItineraryDTO;
import kr.co.iei.plan.model.dto.ItineraryWithPlaceDTO;
import kr.co.iei.plan.model.dto.PlanDTO;
import kr.co.iei.util.PageInfo;
import kr.co.iei.util.PageInfoUtil;

@Service
public class PlanService {
	@Autowired
	private PlanDao planDao;

	@Autowired
	private PageInfoUtil pageInfoUtil;

	public PlanDTO verifyPlan(String loginNickname, int planNo) {
		//1. 존재하는 플래너인지 먼저 확인
		PlanDTO plan = planDao.selectOnePlan(planNo);
		if(plan == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		//2. 존재한다면 열람 권한 확인(+ 계정 즐겨찾기 여부도 조회)
		PlanDTO p = planDao.verifyPlan(loginNickname, planNo);
		if(p == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		return p;
	}

	public boolean isPlanOwner(String loginNickname, int planNo) {
		int count = planDao.isPlanOwner(loginNickname, planNo);
		return count > 0;
	}

	public List selectNearby(double lat, double lng, int radius) {
		List list = planDao.selectNearby(lat, lng, radius);
		return list;
	}

	public List selectPlanItineraries(int planNo) {
		List<ItineraryWithPlaceDTO> list = planDao.selectPlanItinerariesWithPlace(planNo);
		return list;
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
		planStats.setMostPlace(planDao.selectMostPlace());
		return planStats;
	}

	public List<PlaceInfoDTO> selectMostPlace() {
		List<PlaceInfoDTO> mostPlace = planDao.selectMostPlace();
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

	@Transactional
	public int toggleBookmark(int planNo, String memberNickname) {
		int count = planDao.checkBookmark(planNo, memberNickname);
		if(count > 0) {
			count += planDao.deleteBookmark(planNo, memberNickname);
			return 0;
		}else {
			count += planDao.insertBookmark(planNo, memberNickname);
			return 1;
		}
	}

}
