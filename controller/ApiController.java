package kr.co.iei.etc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.iei.util.EmailSender;

@Controller
@RequestMapping(value="/api")
public class ApiController {
	@Autowired
	private EmailSender emailSender;
	
	@GetMapping(value="/email")
	public String email(){
		return "etc/email";		
	}
	
	@PostMapping(value="/sendMail")
	public String sendMail(String emailTitle, String receiver, String emailContent) {
		System.out.println("제목 : "+emailTitle);
		System.out.println("받는사람 : "+receiver);
		System.out.println("내용 : "+emailContent);
		
		emailSender.sendMail(emailTitle,receiver,emailContent);
		
		return "redirect:/api/email";
	}
	
	@ResponseBody
	@GetMapping(value="/sendCode")
	public String sendCode(String receiver) {
		//인증메일 제목 생성
		String emailTitle = "PARK'S WORLD 인증메일입니다.";
		//인증메일용 인증코드 생성
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<6;i++) {
			//숫자(0~9) : r.nextInt(10)
			//대문자(A~Z) : r.nextInt(26)+65
			//소문자(a~z) : r.nextInt(26)+97
			
			int flag = r.nextInt(3);//0,1,2 -> 숫자,대문자,소문자 어떤 거 사용할 지 랜덤으로 결정
			
			if(flag == 0) {
				int randomCode = r.nextInt(10);
				sb.append(randomCode);
			}else if(flag == 1){
				char randomCode = (char)(r.nextInt(26)+65);
				sb.append(randomCode);
			}else {
				char randomCode = (char)(r.nextInt(26)+97);
				sb.append(randomCode);
			}
		}
		String emailContent = "<h1>안녕하세요. PARK'S WORLD 입니다.</h1>"
								+"<h3>인증번호는 "
								+"[<span style='color:red;'>"
								+sb.toString()
								+"</span>]"
								+"입니다.</h3>";
		emailSender.sendMail(emailTitle, receiver, emailContent);
		return sb.toString();
				
	}
	
	@GetMapping(value="/map")
	public String map() {
		return "etc/map";
	}
	
	@GetMapping(value="/pay")
	public String pay() {
		return "etc/pay";
	}
	
	@GetMapping(value="/busan")
	public String busan() {
		return "etc/busan";
	}
	
	@ResponseBody
	@GetMapping(value="/busanPlace")
	public List busanPlace(String pageNo) {
		List list = new ArrayList<BusanPlace>();
		
		String url = "https://apis.data.go.kr/6260000/FoodService/getFoodKr";
		//decode키
		String serviceKey = "8+gfV1yJ0BI919G0r0L2dj0w2ar56ox+UyJJzj2Ea/MfHUiLCLupMI5noV4JfnONv8ioB+RcROar3p4rFY7DIg==";
		String numOfRows = "10";
		
		
		try {
			String result  = Jsoup.connect(url)
					.data("serviceKey",serviceKey)
					.data("numOfRows",numOfRows)
					.data("pageNo",pageNo)
					.data("resultType","json")
					.ignoreContentType(true)	//결과타입을 따로 분류하지 않고 우리가 원하는 형태로 사용
					.get()
					.text();
			System.out.println(result);
			//결과로 받은 문자열을 json 타입으로 변환(JsonParser)
			JsonObject object = (JsonObject)JsonParser.parseString(result);
			JsonObject getFoodKr = object.get("getFoodKr").getAsJsonObject();
			JsonArray items = getFoodKr.get("item").getAsJsonArray();
			for(int i=0;i<items.size();i++) {
				JsonObject item = items.get(i).getAsJsonObject();
				String title = item.get("MAIN_TITLE").getAsString();
				String thumb = item.get("MAIN_IMG_THUMB").getAsString();
				String addr = item.get("ADDR1").getAsString();
				String tel = item.get("CNTCT_TEL").getAsString();
				String intro = item.get("ITEMCNTNTS").getAsString();
				BusanPlace bp = new BusanPlace(title, thumb, addr, tel, intro);
				list.add(bp);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//XML 방식
		/*
		try {
			Document document = Jsoup.connect(url)
				.data("serviceKey",serviceKey)
				.data("numOfRows",numOfRows)
				.data("pageNo",pageNo)
				.ignoreContentType(true)	//결과타입을 따로 분류하지 않고 우리가 원하는 형태로 사용
				.get();
			
			Elements elements = document.select("item");//결과로 받은 xml문서 중 item태그만 선택
			for(int i=0;i<elements.size();i++) {
				Element item = elements.get(i);
				String title = item.select("MAIN_TITLE").text();
				String thumb = item.select("MAIN_IMG_THUMB").text();
				String addr = item.select("MAIN_ADDR1").text();
				String tel = item.select("MAIN_CNTCT_TEL").text();
				String intro = item.select("ITEMCNTNTS").text();
				BusanPlace place = new BusanPlace(title, thumb, addr, tel, intro);
				list.add(place);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	
		return list;
	}
}
