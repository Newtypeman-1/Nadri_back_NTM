package kr.co.iei.plan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.iei.plan.model.dto.ItineraryDTO;
import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.plan.model.dto.PlanDTO;
import kr.co.iei.plan.model.dto.PlanRequestDTO;
import kr.co.iei.plan.model.service.PlanService;
import kr.co.iei.util.FileUtils;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/plan")
public class PlanController {
	@Autowired
	private PlanService planService;
	@Autowired
	private FileUtils fileUtils;
	@Value("${file.root}")
	private String root;

	// 플래너 진입 시 새 플래너인지, 기존 플래너인지 체크 (+ 자신의 플래너가 맞는지도 체크)
	@GetMapping(value = "/verify")
	public ResponseEntity<Map> verifyPlan(@RequestParam String loginNickname, @RequestParam int planNo) {
		// plan_status가 1(공개 상태)이거나, 그렇지 않다면 작성자 본인인지
		// 즉 열람 권한이 있는지 체크 (없을 시 403/404 예외 처리)
		PlanDTO plan = planService.verifyPlan(loginNickname, planNo);

		// 열람 권한이 있다면, 반환할 map을 구성: 플랜 정보 + 저장된 여행지들 + 수정/삭제 권한 여부
		List list = planService.selectPlanItineraries(planNo);
		boolean isOwner = planService.isPlanOwner(loginNickname, planNo);

		Map<String, Object> map = new HashMap<>();
		map.put("plan", plan);
		map.put("itineraries", list);
		map.put("isOwner", isOwner);

		return ResponseEntity.ok(map);
	}

	// 반경 내 장소 데이터 수집
	@GetMapping(value = "{memberNickname}/nearby")
	public ResponseEntity<Map<String, Object>> selectPagedNearby(@PathVariable String memberNickname, @RequestParam double lat, @RequestParam double lng,
			@RequestParam double width, @RequestParam double height, @RequestParam int page, @RequestParam int size, @RequestParam int sortOption, @RequestParam(required = false) Integer filterOption) {
		Map<String, Object> map = planService.selectPagedNearby(memberNickname, lat, lng, width, height, page, size, sortOption, filterOption);
		
		return ResponseEntity.ok(map);
	}

	@GetMapping("/stats")
	public ResponseEntity<AdminStatsDTO> selectPlanStats() {
		AdminStatsDTO planStats = planService.selectPlanStats();
		return ResponseEntity.ok(planStats);
	}

	@GetMapping("/mostPlace")
	public ResponseEntity<List> selectmostPlace(@RequestParam(required = false) String area) {
		List<PlaceInfoDTO> mostPlace = planService.selectMostPlace();
		return ResponseEntity.ok(mostPlace);
	}

	// 플래너 썸네일 업로드
	@PostMapping(value = "/thumb")
	public ResponseEntity<String> uploadThumb(@RequestParam("file") MultipartFile file, @RequestParam(value="planNo", required = false) Integer planNo) {
		String savepath = root + "/plan/thumbnail/";
		if(planNo != null) {
			PlanDTO plan = planService.selectOnePlan(planNo);
			String oldFilepath = plan.getPlanThumb();
			fileUtils.delete(savepath, oldFilepath);
		}
		String filepath = fileUtils.upload(savepath, file);
		return ResponseEntity.ok(filepath);
	}

	// 플래너 저장
	@PostMapping(value = "/save")
	public ResponseEntity<Boolean> savePlanner(@RequestBody Map<String, Object> planData) {
		ObjectMapper om = new ObjectMapper();
		PlanDTO plan = om.convertValue(planData.get("tripPlanData"), PlanDTO.class);

		List raw = (List) planData.get("itineraryList");
		List<ItineraryDTO> list = new ArrayList<>();
		for (Object obj : raw) {
			Map<String, Object> map = (Map<String, Object>) obj;
			ItineraryDTO i = new ItineraryDTO();
			i.setItineraryDate((String) map.get("itineraryDate"));
			i.setStartLocation(map.get("startLocation") == null ? 0 : (Integer) map.get("startLocation"));
			i.setTransport((String) map.get("transport"));
			i.setEndLocation((Integer) map.get("endLocation"));
			i.setItineraryOrder((Integer) map.get("itineraryOrder"));
			list.add(i);
		}
		boolean result = planService.insertPlan(plan, list);

		return ResponseEntity.ok(result);
	}

	// 플래너 수정
	@PutMapping(value = "/update")
	public ResponseEntity<Boolean> updatePlanner(@RequestBody Map<String, Object> planData) {
		ObjectMapper om = new ObjectMapper();
		PlanDTO plan = om.convertValue(planData.get("tripPlanData"), PlanDTO.class);

		List raw = (List) planData.get("itineraryList");
		List<ItineraryDTO> list = new ArrayList<>();
		for (Object obj : raw) {
			Map<String, Object> map = (Map<String, Object>) obj;
			ItineraryDTO i = new ItineraryDTO();
			i.setItineraryDate((String) map.get("itineraryDate"));
			i.setStartLocation(map.get("startLocation") == null ? 0 : (Integer) map.get("startLocation"));
			i.setTransport((String) map.get("transport"));
			i.setEndLocation((Integer) map.get("endLocation"));
			i.setItineraryOrder((Integer) map.get("itineraryOrder"));
			i.setPlanNo(plan.getPlanNo());
			list.add(i);
		}
		boolean result = planService.updatePlan(plan, list);

		return ResponseEntity.ok(result);
	}
	
	//플래너 즐겨찾기
	@PatchMapping(value="{planNo}/{memberNickname}")
	public ResponseEntity<Integer> toggleBookmark(@PathVariable int planNo, @PathVariable String memberNickname){
		int result = planService.toggleBookmark(planNo, memberNickname);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping
	public ResponseEntity<List> selectPlanList(@ModelAttribute PlanRequestDTO request){
		List<PlanDTO> planList = planService.selectPlanList(request);
		return ResponseEntity.ok(planList);
	}
	
	//플래너 삭제
	@DeleteMapping(value="{planNo}/{memberNickname}")
	public ResponseEntity<Integer> deletePlan(@PathVariable int planNo, @PathVariable String memberNickname){
		int result = planService.deletePlan(planNo, memberNickname);
		return ResponseEntity.ok(result);
	}
	
//	//플래너 복사(다른 사용자 플래너를 내 플래너에 추가)
//	@PostMapping(value="{planNo}/copy/{memberNickname}")
//	public ResponseEntity<Integer> copyPlan(@PathVariable int planNo, @PathVariable String memberNickname){
//		int result = planService.copyPlan(planNo, memberNickname);
//		return ResponseEntity.ok(result);
//	}
	
}
