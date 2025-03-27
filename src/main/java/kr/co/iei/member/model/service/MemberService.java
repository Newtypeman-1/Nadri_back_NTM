package kr.co.iei.member.model.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.dao.MemberDao;
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
		int result = memberDao.updatePw(member);
		return result;
	}

}
