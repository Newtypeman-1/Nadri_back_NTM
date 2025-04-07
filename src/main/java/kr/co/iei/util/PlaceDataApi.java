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
	
	
	
    // ✅ 관광지 상세 정보 보충 및 spot 저장 ==> placeId(126508)하나만 테스트 완료
    @Transactional
    public void spotPlaceData() {
        List<PlaceInfoDTO> spots = placeDao.selectSpotDetail();  // 변경된 메서드명 사용


        PlaceInfoDTO place = new PlaceInfoDTO();
        place.setPlaceId(126508);
            try {
                String result = Jsoup.connect("https://apis.data.go.kr/B551011/KorService1/detailIntro1")
                        .data("serviceKey", "pvRJJMc1wFDv3/ZHxWYn6lN1hwI7T4OcYmBiX+0kaTuuBpz4Y/l13ZmJKoQgn2utTqNdOcmz3Orokb0SN0yhKA==")
                        .data("MobileOS", "ETC")
                        .data("MobileApp", "nadri")
                        .data("_type", "json")
                        .data("contentId", "126508")
                        .data("contentTypeId", "12")
                        .ignoreContentType(true)
                        .get()
                        .text();
                JsonObject json = JsonParser.parseString(result).getAsJsonObject();

                JsonArray items = json.getAsJsonObject()
				                        .getAsJsonObject("response")
				                        .getAsJsonObject("body")
				                        .getAsJsonObject("items")
				                        .getAsJsonArray("item");

                // ✅ place_info 보충 정보

                for (JsonElement element : items) {
                    JsonObject itemObj = element.getAsJsonObject();

                    String useTime = itemObj.has("usetime") ? itemObj.get("usetime").getAsString() : null;
                    String restDate = itemObj.has("restdate") ? itemObj.get("restdate").getAsString() : null;
                    String parking = itemObj.has("parking") ? itemObj.get("parking").getAsString() : null;

                   //DB 저장
//                 System.out.println("usetime: " + useTime);
//                 System.out.println("restdate: " + restDate);
//                 System.out.println("parking: " + parking);
                   place.setUseTime(useTime);
                   place.setRestDate(restDate);
                   place.setParking(parking);
                }
                

                placeDao.updateDetailInfo(place);

                // ✅ spot 테이블 데이터 삽입
                SpotDTO spot = new SpotDTO();
                for (JsonElement element : items) {
                    JsonObject itemObj = element.getAsJsonObject();
                    
                    spot.setPlaceId(place.getPlaceId());
                    spot.setHeritage1(itemObj.has("heritage1") ? itemObj.get("heritage1").getAsInt() : null);
                    spot.setHeritage2(itemObj.has("heritage2") ? itemObj.get("heritage2").getAsInt() : null);
                    spot.setHeritage3(itemObj.has("heritage3") ? itemObj.get("heritage3").getAsInt() : null);
                    spot.setUseSeason(itemObj.has("useseason") ? itemObj.get("useseason").getAsString() : null); // 컬럼명에 맞춤

                placeDao.insertSpotInfo(spot);
                }

            } catch (Exception e) {
                System.out.println("❌ 에러 발생 - place_id: " + place.getPlaceId());
                e.printStackTrace();
            }


        System.out.println("✅ 관광지 상세 데이터 보충 완료");
    
    }
}

	
	
	
	//맨처음 데이터 불러온 코드
//	@GetMapping(value="/commonContent")
//	public List spotPlace() {
//		
//		//지역기반 관광정보 조회(contentTypeId: 12)
//		String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
//		try {
//			int pageNo = 1;
//			while(pageNo<33) {
//				List list = new ArrayList<PlaceInfoDTO>();
//				String result = Jsoup.connect(url)
//						.data("serviceKey", serviceKey)
//						.data("MobileOS", "etc")
//						.data("MobileApp", "nadri")
//						.data("_type","json")
//						.data("numOfRows", "500")
//						.data("pageNo", String.valueOf(pageNo))
//						.data("contentTypeId", "39")
//						.ignoreContentType(true)
//						.get()
//						.text();
//				System.out.println(result);

//				// JSON 문자열을 JsonObject로 변환(배운버전)
//				JsonObject jsonObject = (JsonObject)JsonParser.parseString(result);
//				// "response" 객체 가져오기(배운버전)
//				JsonObject response = jsonObject.get("response").getAsJsonObject();

//				// "body" 객체 가져오기
//				JsonObject body = response.getAsJsonObject("body");
//				// "items" 객체 가져오기

//				JsonObject items = body.getAsJsonObject("items");
//				// "item" 배열 가져오기

//				JsonArray itemArray = items.getAsJsonArray("item");

//				for(int i=0;i<itemArray.size();i++) {
//					JsonObject item = itemArray.get(i).getAsJsonObject();
//					String contentThumb = item.get("firstimage").getAsString();
//					double mapLat = Double.parseDouble(item.get("mapx").getAsString());
//					double mapLng = Double.parseDouble(item.get("mapy").getAsString());
//					String areaCode = item.get("areacode").getAsString();
//					String sigunguCode = item.get("sigungucode").getAsString();
//					String contentCat1 = item.get("cat1").getAsString();
//					String contentCat2 = item.get("cat2").getAsString();
//					String contentCat3 = item.get("cat3").getAsString();
//					String contentTel = item.get("tel").getAsString();
//					String contentTitle = item.get("title").getAsString();
//					String contentAddr = item.get("addr1").getAsString();
//					int contentId = item.get("contentid").getAsInt();
//					int contentTypeId = Integer.parseInt(item.get("contenttypeid").getAsString());
//					PlaceInfoDTO ccd = new PlaceInfoDTO(contentThumb, mapLat, mapLng, areaCode, sigunguCode, contentCat1, contentCat2, contentCat3, contentTel, contentTitle, contentAddr, contentId, contentTypeId, contentTitle, sigunguCode, contentAddr);
//					list.add(ccd);
//				}
//				int commonResult = placeService.insertCommon(list);
//				pageNo++;
//				
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//				
//	return null;
//	}
	

