package kr.co.iei.member.controller;

import java.util.HashMap;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/kakao")
public class KakaoController {
	
	@PostMapping(value="/login")
	public ResponseEntity<Integer> kakaoLogin(@RequestBody String code, HttpServletResponse httpServletResponse){
		System.out.println(code);
		User user = authService.oAuthLogin(code, httpServletResponse);
        return BaseResponse.onSuccess(UserConverter.toJoinResultDTO(user));
		
		
	}
}
