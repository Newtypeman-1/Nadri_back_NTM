package kr.co.iei.member.controller;

import java.util.HashMap;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.iei.member.model.dto.KakaoUser;
import kr.co.iei.member.model.service.KakaoLoginService;

@RestController
@RequestMapping(value="/kakao")
@CrossOrigin("*")
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;

    @Autowired
    public KakaoController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @PostMapping(value="/login")
    public ResponseEntity<String> kakaoLogin(@RequestBody String code) {
    	System.out.println(code);      
        // "code" 부분에서 값을 추출
        String accessCode = code.split(":\"")[1].split("\"")[0];     
        // 결과 출력
        System.out.println(accessCode);
        try {
            // Step 1: 카카오에서 액세스 토큰을 요청
            String accessToken = kakaoLoginService.getAccessToken(accessCode);
            System.out.println("accessToken : " + accessToken);
            // Step 2: 액세스 토큰을 이용해 사용자 정보를 요청
            KakaoUser kakaoUser = kakaoLoginService.getUserInfo(accessToken);
            System.out.println("kakaoUser : " + kakaoUser.getEmail());
            // 사용자 이메일 정보 반환
            return ResponseEntity.ok(kakaoUser.getEmail());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("카카오 로그인 오류");
        }
    }
}
