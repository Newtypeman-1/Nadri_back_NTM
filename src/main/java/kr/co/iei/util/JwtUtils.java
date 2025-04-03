package kr.co.iei.util;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.co.iei.member.model.dto.LoginMemberDTO;

@Component
public class JwtUtils {
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.expire-hour}")
	private int expireHour;
	@Value("${jwt.expire-hour-refresh}")
	private int expireHourRefresh;
	
	//1시간짜리 토큰 생성
	public String createAccessToken(String memberEmail, int memberType) {
		//1. 작성해둔 키값을 이용해서 암호화 코드를 생성
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
		//2. 토큰의 생성시간, 만료시간 설정(Date 타입)
		Calendar c = Calendar.getInstance();
		Date startTime = c.getTime();	//토큰생성시간 -> 현재시간
		c.add(Calendar.HOUR, expireHour);//현재시간에 1시간 뒤
		Date expireTime = c.getTime();	//토큰만료시간 -> 현재시간+1시간
		
		//3. 토큰생성
		String token = Jwts.builder()							//JWT 생성시작
						.issuedAt(startTime)					//토큰 발행시간
						.expiration(expireTime)					//토큰 만료시간
						.signWith(key)							//암호화 서명
						.claim("memberEmail", memberEmail)		//토큰에 포함할 회원정보 세팅
						.claim("memberType", memberType)		//토큰에 포함할 회원정보 세팅
						.compact();								//생성	
		return token;
	}
	//8760시간(1년)토큰 발행
	public String createRefreshToken(String memberEmail, int memberType) {
		//1. 작성해둔 키값을 이용해서 암호화 코드를 생성
			SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
			//2. 토큰의 생성시간, 만료시간 설정(Date 타입)
			Calendar c = Calendar.getInstance();
			Date startTime = c.getTime();	//토큰생성시간 -> 현재시간
			c.add(Calendar.HOUR, expireHourRefresh);//현재시간에 1시간 뒤
			Date expireTime = c.getTime();	//토큰만료시간 -> 현재시간+1시간
				
			//3. 토큰생성
			String token = Jwts.builder()							//JWT 생성시작
							.issuedAt(startTime)					//토큰 발행시간
							.expiration(expireTime)					//토큰 만료시간
							.signWith(key)							//암호화 서명
							.claim("memberEmail", memberEmail)		//토큰에 포함할 회원정보 세팅
							.claim("memberType", memberType)		//토큰에 포함할 회원정보 세팅
							.compact();								//생성	
			return token;
	}
	public LoginMemberDTO checkToken(String accessToken) {
		//1. 작성해둔 키값을 이용해서 암호화 코드를 생성
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
		Claims claims = (Claims) Jwts.parser()
									.verifyWith(key)
									.build()
									.parse(accessToken)
									.getPayload();
		String memberEmail = (String)claims.get("memberEmail");
		int memberLevel = (int)claims.get("memberLevel");
		LoginMemberDTO loginMember = new LoginMemberDTO();
		loginMember.setMemberEmail(memberEmail);
		loginMember.setMemberLevel(memberLevel);
		return loginMember;
	}
	
}
