package kr.co.iei.plan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.plan.model.service.PlanService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/plan")
public class PlanController {
	@Autowired
	private PlanService planService;
	
	//유저 마커 반경 내 장소 데이터 수집
	@GetMapping(value="/nearby")
	public ResponseEntity<List> selectNearby(@RequestParam double lat, @RequestParam double lng, @RequestParam int radius){
		List list = planService.selectNearby(lat, lng, radius);
		return ResponseEntity.ok(list);
	}
}
