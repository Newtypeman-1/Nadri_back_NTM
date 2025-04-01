package kr.co.iei.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.iei.tour.model.dto.ContentCommonDTO;
import kr.co.iei.tour.model.service.TourService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/content")
public class ContentDataController {
	@Autowired
	private TourService tourService;
	@Value(value="${tourapi.key}")
	private String serviceKey;
	
	@GetMapping(value="/commonContent")
	public List spotPlace() {
		
		//지역기반 관광정보 조회(contentTypeId: 12)
		String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
		try {
			int pageNo = 1;
			while(pageNo<33) {
				List list = new ArrayList<ContentCommonDTO>();
				String result = Jsoup.connect(url)
						.data("serviceKey", serviceKey)
						.data("MobileOS", "etc")
						.data("MobileApp", "nadri")
						.data("_type","json")
						.data("numOfRows", "500")
						.data("pageNo", String.valueOf(pageNo))
						.data("contentTypeId", "39")
						.ignoreContentType(true)
						.get()
						.text();
				System.out.println(result);
				// JSON 문자열을 JsonObject로 변환(배운버전)
				JsonObject jsonObject = (JsonObject)JsonParser.parseString(result);
				// "response" 객체 가져오기(배운버전)
				JsonObject response = jsonObject.get("response").getAsJsonObject();
				// "body" 객체 가져오기
				JsonObject body = response.getAsJsonObject("body");
				// "items" 객체 가져오기
				JsonObject items = body.getAsJsonObject("items");
				// "item" 배열 가져오기
				JsonArray itemArray = items.getAsJsonArray("item");
				for(int i=0;i<itemArray.size();i++) {
					JsonObject item = itemArray.get(i).getAsJsonObject();
					String contentThumb = item.get("firstimage").getAsString();
					double mapLat = Double.parseDouble(item.get("mapx").getAsString());
					double mapLng = Double.parseDouble(item.get("mapy").getAsString());
					String areaCode = item.get("areacode").getAsString();
					String sigunguCode = item.get("sigungucode").getAsString();
					String contentCat1 = item.get("cat1").getAsString();
					String contentCat2 = item.get("cat2").getAsString();
					String contentCat3 = item.get("cat3").getAsString();
					String contentTel = item.get("tel").getAsString();
					String contentTitle = item.get("title").getAsString();
					String contentAddr = item.get("addr1").getAsString();
					int contentId = item.get("contentid").getAsInt();
					int contentTypeId = Integer.parseInt(item.get("contenttypeid").getAsString());
					ContentCommonDTO ccd = new ContentCommonDTO(contentThumb, mapLat, mapLng, areaCode, sigunguCode, contentCat1, contentCat2, contentCat3, contentTel, contentTitle, contentAddr, contentId, contentTypeId);
					list.add(ccd);
				}
				int commonResult = tourService.insertCommon(list);
				pageNo++;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	return null;
	}
	
	//지역기반 관광정보 조회(contentTypeId: 12)
}
