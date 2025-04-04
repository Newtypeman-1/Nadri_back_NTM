package kr.co.iei.place.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.service.PlaceService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/place")
public class PlaceController {
	@Autowired
	private PlaceService placeService;
	
	@GetMapping
	public ResponseEntity<Map> tourList(@RequestParam int reqPage){
		Map map = placeService.selectPlaceList(reqPage);
		return ResponseEntity.ok(map);
	}
	
	@GetMapping(value="/spot")
	public ResponseEntity<Map> spotList(@RequestParam int reqPage){
		Map map = placeService.selectSpotList(reqPage);
		return ResponseEntity.ok(map);
	}
	
	
	
	
	
	
	
	

}
