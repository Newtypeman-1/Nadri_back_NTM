package kr.co.iei.tour.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/tour")
public class TourController {
	@Value(value="${tourapi.key}")
	private String serviceKey;
	
	@GetMapping(value="/spotPlace")
	public List spotPlace() {
		List list = new ArrayList<SpotPlace>();
		
		String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
		
		try {
			String result = Jsoup.connect(url)
					.data("serviceKey", serviceKey)
					.data("MobileOS", "etc")
					.data("MobileApp", "nadri")
					.data("_type","json")
					.ignoreContentType(true)
					.get()
					.text();
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	return list;
		
		
	}
	
	
	

}
