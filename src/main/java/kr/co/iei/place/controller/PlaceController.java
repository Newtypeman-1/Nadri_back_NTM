package kr.co.iei.place.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.iei.place.model.dto.CategoryDTO;
import kr.co.iei.place.model.dto.PlaceFilterRequest;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.service.PlaceService;
import kr.co.iei.review.model.dto.PlaceImgDTO;
import kr.co.iei.review.model.service.ReviewService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/place")
public class PlaceController {
	@Autowired
	private PlaceService placeService;

	// 플레이스 리스트 조회(북마크 상태 포함)
	@GetMapping
	public ResponseEntity<Map> placeList(@RequestParam int reqPage, @RequestParam int order, @RequestParam(required = false) String memberNickname, @RequestParam(required = false) int[] id) {
		int numPerPage = 12;
		Map map = placeService.selectALLPlaceList(reqPage, order, memberNickname, numPerPage, id);
		return ResponseEntity.ok(map);
	}
	
	// 플레이스 리스트 필터적용 조회
	@PostMapping("/filter")
	public ResponseEntity<Map<String, Object>> getFilteredPlaceList(@RequestBody PlaceFilterRequest request) {
        Map<String, Object> result = placeService.selectFilteredPlaceList(request);
        return ResponseEntity.ok(result);
	}

	// 플레이스 타입 아이디 조회
	@GetMapping("/type")
	public ResponseEntity<List> placeType() {
		List<CategoryDTO> placeType = placeService.selectPlaceType();
		return ResponseEntity.ok(placeType);
	}

	@GetMapping("/category")
	public ResponseEntity<Map> placeCategory() {
		Map<String, List<CategoryDTO>> category = placeService.selectPlaceCategory();
		return ResponseEntity.ok(category);
	}

	@GetMapping("/area")
	public ResponseEntity<List> placeArea() {
		List<CategoryDTO> area = placeService.selectPlaceArea();
		return ResponseEntity.ok(area);
	}

	// 상세페이지 조회(좋아요 상태 추가)
	@GetMapping("/detail")
	public ResponseEntity<PlaceInfoDTO> placeDetail(@RequestParam int placeId, @RequestParam(required = false) String memberNickname) {
		PlaceInfoDTO place = placeService.selectOnePlace(placeId, memberNickname);
		return ResponseEntity.ok(place);
	}

	// 즐겨찾기 토글
	@PostMapping("/bookmark/toggle")
	public ResponseEntity<Integer> toggleBookmark(@RequestParam String memberNickname, @RequestParam int placeId) {
		int result = placeService.toggleBookmark(memberNickname, placeId);
		return ResponseEntity.ok(result); // 1: 등록, 0: 해제
	}
	
	// 조회수 저장
	@PostMapping("/detail/{placeId}/click")
	public ResponseEntity<Map<String, Object>> increaseViewCount(@PathVariable int placeId) {
	    int updatedCount = placeService.increaseViewCount(placeId); // 조회수 증가 후 count 리턴하도록 수정
	    Map<String, Object> result = new HashMap<>();
	    result.put("result", "success");
	    result.put("viewCount", updatedCount);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/images/{placeId}")
	public ResponseEntity<List<PlaceImgDTO>> selectPlaceImages(@PathVariable int placeId) {
		List<PlaceImgDTO> list = placeService.selectImagesByPlaceId(placeId);

		return ResponseEntity.ok(list);
	}
	
	
	
	
}
