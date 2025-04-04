package kr.co.iei.plan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.plan.model.dto.PlanDTO;
import kr.co.iei.plan.model.service.PlanService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/plan")
public class PlanController {
	@Autowired
	private PlanService planService;
	
	//플래너 진입 시 새 플래너인지, 기존 플래너인지 체크 (+ 자기 그룹의 플래너가 맞는지도 체크)
	@GetMapping(value="/verify/{planNo}")
	public ResponseEntity<Boolean> verifyPlan(@RequestHeader("Authorization") String refreshToken, @PathVariable int planNo){
//		PlanDTO plan = planService.selectOnePlan(planNo); 이렇게 해서 일단 plan_status부터 체크하도록 바꿔야 함.
		PlanDTO plan = planService.verifyPlan(refreshToken, planNo);
		return ResponseEntity.ok(plan != null);
	}
	
	//유저 마커 반경 내 장소 데이터 수집
	@GetMapping(value="/nearby")
	public ResponseEntity<List> selectNearby(@RequestParam double lat, @RequestParam double lng, @RequestParam int radius){
		List list = planService.selectNearby(lat, lng, radius);
		return ResponseEntity.ok(list);
	}
	
}
