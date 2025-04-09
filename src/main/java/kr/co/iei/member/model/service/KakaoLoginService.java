package kr.co.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.iei.member.model.dto.KakaoUser;

@Service
public class KakaoLoginService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    @Value("${kakao.client.secret}")
    private String clientSecret;
    
    private final RestTemplate restTemplate;

    @Autowired
    public KakaoLoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(String accessCode) {
    	System.out.println(accessCode);
        String url = "https://kauth.kakao.com/oauth/token";       
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        System.out.println(clientId);
        System.out.println(redirectUri);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);
        params.add("code", accessCode);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
 

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // 응답 상태 코드와 바디를 출력하여 확인
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            // 액세스 토큰 파싱
            return parseAccessToken(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("카카오 API 호출 중 오류 발생", e);  // 예외 던지기
        }
    }

    private String parseAccessToken(String response) {
        // 예: {"access_token":"YOUR_ACCESS_TOKEN", ...}
        // 여기서는 Gson을 사용하여 JSON에서 액세스 토큰을 추출합니다.
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    public KakaoUser getUserInfo(String accessToken) {
        System.out.println("accessToken : " + accessToken);
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("response : " + response);
        return parseUserInfo(response.getBody());
    }

    private KakaoUser parseUserInfo(String response) {
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        String userId = jsonObject.get("id").getAsString();
        String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();

        return new KakaoUser(userId, nickname);
    }
}