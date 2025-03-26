package kr.co.iei.etc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.util.EmailSender;



@CrossOrigin("*")
@RestController
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
	
    @GetMapping(value="/sendCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
		System.out.println(email); 
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
		emailSender.sendMail(emailTitle, email, emailContent);
		return ResponseEntity.ok(sb.toString());			
	}	
}
