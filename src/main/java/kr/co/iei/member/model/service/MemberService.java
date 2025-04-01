package kr.co.iei.member.model.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.member.model.dto.MemberDTO;
import kr.co.iei.util.JwtUtils;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private JwtUtils jwtUtil;
	
	//이메일 중복검사
	public int existsEmail(String memberEmail) {
		int result = memberDao.existsEmail(memberEmail);
		return result;
	}
	
	//닉네임 중복검사
	public int exists(String memberNickname) {
		int result = memberDao.exists(memberNickname);
		return result;
	}
	
	//회원가입
    @Transactional
	public int insertMember(MemberDTO member) {
		String memberPw = member.getMemberPw();
		String encPw = encoder.encode(memberPw);
		member.setMemberPw(encPw);
		int result = memberDao.insertMember(member);
		return result;
	}

    //비밀번호 재설정
	public int updatePw(MemberDTO member) {
		String encPw = encoder.encode(member.getMemberPw());
		member.setMemberPw(encPw);
		int result = memberDao.updatePw(member);
		return result;
	}

	public LoginMemberDTO login(MemberDTO member) {

		MemberDTO m = memberDao.selectOneMember(member.getMemberEmail());
		if(m != null && encoder.matches(member.getMemberPw(), m.getMemberPw())) {
			String accessToken = jwtUtil.createAccessToken(m.getMemberEmail(), m.getMemberType());
			System.out.println("accessToken : "+accessToken);
			String refreshToken = jwtUtil.createRefreshToken(m.getMemberEmail(),m.getMemberType());
			LoginMemberDTO loginMember = new LoginMemberDTO(accessToken, refreshToken, m.getMemberEmail(), m.getMemberNickname(), m.getMemberType());
			System.out.println(loginMember);
			return loginMember;
		}
		return null;
	}

	public MemberDTO selectMember(String memberNickname) {
		MemberDTO member = memberDao.selectOneMember(memberNickname);
		return member;
	}
}
