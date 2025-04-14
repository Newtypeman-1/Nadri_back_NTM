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
	
    @GetMapping(value="/sendCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
		
		//인증메일 제목 생성
		String emailTitle = "NADRI 회원가입 인증메일입니다.";
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
		String emailContent = "<h2>안녕하세요, <span style='color:green; font-size:36px; font-weight:bold;'>NADRI</span> 입니다.</h2>"
								+"<h4>회원가입을 위한 인증 코드가 필요합니다. 아래의 코드를 입력하여 인증을 완료해 주세요. </h3>"
								+"<h3>인증 코드 : "
								+"[<span style='color:red;'>"
								+sb.toString()
								+"</span>]</h3>"
								+"<h4>※ 이 코드는 3분 내에만 유효합니다. </h3>"
								+"<h4>NADRI 드림.</h3>";
		emailSender.sendMail(emailTitle, email, emailContent);
		return ResponseEntity.ok(sb.toString());			
	}	
}

