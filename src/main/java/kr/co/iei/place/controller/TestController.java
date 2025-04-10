package kr.co.iei.place.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.place.model.service.PlaceService;
import kr.co.iei.util.PlaceDataApi;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/api")
public class TestController {
	@Autowired
	private PlaceDataApi placeDataApi;
	
	@GetMapping
	private void insertPlace() {
		placeDataApi.insertPlaceInfoFromApi();
	}
	

	
}
