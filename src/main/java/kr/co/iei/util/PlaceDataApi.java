package kr.co.iei.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.annotation.PostConstruct;
import kr.co.iei.place.model.dao.PlaceDao;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.SpotDTO;
import kr.co.iei.place.model.service.PlaceService;

@CrossOrigin("*")
@Service
public class PlaceDataApi {
	@Value(value="${placeapi.key}")
	private String serviceKey;
	@Autowired
	private PlaceDao placeDao;
	
	
	//공통정보 인서트
    @Transactional
	public void insertPlaceInfoFromApi() {
	    String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
	    int pageNo = 1;

	    try {
	        while (pageNo <= 32) {
	            String result = Jsoup.connect(url)
	                    .data("serviceKey", "pvRJJMc1wFDv3/ZHxWYn6lN1hwI7T4OcYmBiX+0kaTuuBpz4Y/l13ZmJKoQgn2utTqNdOcmz3Orokb0SN0yhKA==")
	                    .data("MobileOS", "ETC")
	                    .data("MobileApp", "nadri")
	                    .data("_type", "json")
	                    .data("numOfRows", "500")
	                    .data("pageNo", String.valueOf(pageNo))
	                    .data("contentTypeId", "39") // 관광지
	                    .ignoreContentType(true)
	                    .get()
	                    .text();

	            JsonObject json = JsonParser.parseString(result).getAsJsonObject();
	            
	            System.out.println("pageNo : "+pageNo );
	            JsonArray items = json.getAsJsonObject("response")
	                                   .getAsJsonObject("body")
	                                   .getAsJsonObject("items")
	                                   .getAsJsonArray("item");

	            List<PlaceInfoDTO> list = new ArrayList<>();

	            for (JsonElement el : items) {
	                JsonObject item = el.getAsJsonObject();

	                PlaceInfoDTO dto = new PlaceInfoDTO();
	                dto.setPlaceId(item.get("contentid").getAsInt());
	                dto.setPlaceTypeId(item.get("contenttypeid").getAsInt());
	                dto.setPlaceTitle(item.has("title") ? item.get("title").getAsString() : null);
	                dto.setPlaceAddr(item.has("addr1") ? item.get("addr1").getAsString() : null);
	                dto.setPlaceTel(item.has("tel") ? item.get("tel").getAsString() : null);
	                dto.setAreaCode(item.has("areacode") ? item.get("areacode").getAsString() : null);
	                dto.setSigunguCode(item.has("sigungucode") ? item.get("sigungucode").getAsString() : null);
	                dto.setPlaceCat1(item.has("cat1") ? item.get("cat1").getAsString() : null);
	                dto.setPlaceCat2(item.has("cat2") ? item.get("cat2").getAsString() : null);
	                dto.setPlaceCat3(item.has("cat3") ? item.get("cat3").getAsString() : null);
	                dto.setMapLat(item.has("mapy") ? item.get("mapy").getAsDouble() : 0);
	                dto.setMapLng(item.has("mapx") ? item.get("mapx").getAsDouble() : 0);
	                dto.setPlaceThumb(item.has("firstimage") ? item.get("firstimage").getAsString() : null);

	                list.add(dto);
	            }

	            placeDao.insertPlaceInfoList(list);
	            pageNo++;
	        }

	        System.out.println("✅ place_info 데이터 초기화 완료!");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}

	

	

