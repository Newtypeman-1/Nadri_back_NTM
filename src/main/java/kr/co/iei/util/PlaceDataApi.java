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
    
    
    //overview 인서트 코드
    @Transactional
    public void insertOverview() {
        List<PlaceInfoDTO> places = placeDao.selectPlaces(); // 전체 place_id 리스트 조회

        for (PlaceInfoDTO place : places) {
            try {
                String result = Jsoup.connect("https://apis.data.go.kr/B551011/KorService1/detailCommon1")
                        .data("serviceKey", "p6OE41A6Y/J5UyvOHeyDbZAiqvEt6lw710QuQJKC4qtMKfx3kAHA00zv0/0OS9bD8KcOvnP2GtRdNlYFkPfO/w==") // ← @Value로 주입해도 됨
                        .data("MobileOS", "ETC")
                        .data("MobileApp", "nadri")
                        .data("_type", "json")
                        .data("contentId", String.valueOf(place.getPlaceId()))
                        .data("overviewYN", "Y")
                        .ignoreContentType(true)
                        .get()
                        .text();

                JsonArray items = JsonParser.parseString(result)
                        .getAsJsonObject()
                        .getAsJsonObject("response")
                        .getAsJsonObject("body")
                        .getAsJsonObject("items")
                        .getAsJsonArray("item");

                for (JsonElement element : items) {
                    JsonObject item = element.getAsJsonObject();
                    String placeOverview = item.has("overview") ? item.get("overview").getAsString() : null;

                    // DTO에 overview 값 설정
                    place.setPlaceOverview(placeOverview);
                    placeDao.updateOverview(place); // ✅ DB 업데이트
                    System.out.println("✅ place_id " + place.getPlaceId() + " - 개요 업데이트 완료");
                }

            } catch (Exception e) {
                System.out.println("❌ 개요 업데이트 실패 - place_id: " + place.getPlaceId());
                e.printStackTrace();
            }
        }

        System.out.println("✅ 전체 overview 데이터 보충 완료");
    }
	
}
	
    
	

	

