package kr.co.iei.tour.controller;

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

import kr.co.iei.tour.model.dto.ContentCommonDTO;
import kr.co.iei.tour.model.dto.SpotPlace;
import kr.co.iei.tour.model.service.TourService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/content")
public class TourController {
	@Value(value="${tourapi.key}")
	private String serviceKey;
	@Autowired
	private TourService tourService;
	
	@GetMapping
	public ResponseEntity<Map> tourList(@RequestParam int reqPage){
		Map map = tourService.selectTourList(reqPage);
		return ResponseEntity.ok(map);
	}
	
	

}
