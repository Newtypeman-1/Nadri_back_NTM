package kr.co.iei.member.model.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.member.model.dto.MemberDTO;
import kr.co.iei.util.FileUtils;
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
    
	//소셜회원가입
    @Transactional
    public int socialJoin(MemberDTO member) {
    	System.out.println(member);
		int result = memberDao.socialJoin(member);
    	return result;
    }

    //비밀번호 재설정
	public int updatePw(MemberDTO member) {
		String encPw = encoder.encode(member.getMemberPw());
		member.setMemberPw(encPw);
		int result = memberDao.updatePw(member);
		return result;
	}
	
	//로그인
	public LoginMemberDTO login(MemberDTO member) {
		MemberDTO m = memberDao.selectOneMember(member.getMemberEmail());
		if(m != null && encoder.matches(member.getMemberPw(), m.getMemberPw())) {
			String accessToken = jwtUtil.createAccessToken(m.getMemberEmail(), m.getMemberLevel());
			System.out.println("accessToken : "+accessToken);
			String refreshToken = jwtUtil.createRefreshToken(m.getMemberEmail(),m.getMemberLevel());
			LoginMemberDTO loginMember = new LoginMemberDTO(accessToken, refreshToken, m.getMemberEmail(), m.getMemberNickname(), m.getMemberLevel(), m.getMemberNo());
			System.out.println(loginMember);
			return loginMember;
		}
		return null;
	}
	
	//소셜로그인
	public LoginMemberDTO socialLogin(String userEmail) {
		System.out.println(userEmail);
		MemberDTO m = memberDao.socialLogin(userEmail);
		if(m != null) {
			String accessToken = jwtUtil.createAccessToken(m.getMemberEmail(), m.getMemberLevel());
			System.out.println("accessToken : "+accessToken);
			String refreshToken = jwtUtil.createRefreshToken(m.getMemberEmail(),m.getMemberLevel());
			LoginMemberDTO loginMember = new LoginMemberDTO(accessToken, refreshToken, m.getMemberEmail(), m.getMemberNickname(), m.getMemberLevel(), m.getMemberNo());
			System.out.println(loginMember);
			return loginMember;
		}
		return null;
	}
	
	//소셜 이메일 확인
	public int isSocial(String email) {
		int result = memberDao.isSocial(email);
		return result;
	}
	
	//마이페이지 회원정보 출력
	public MemberDTO selectMemberInfo(String memberNickname) {
		MemberDTO member = memberDao.selectMemberInfo(memberNickname);
		return member;
	}
	//마이페이지 회원정보 수정
	public int updateMember(MemberDTO member) {
		int result = memberDao.updateMember(member);
		return result;
	}
	//마이페이지 회원정보 수정2
	public int updateMember2(MemberDTO member) {
		int result = memberDao.updateMember2(member);
		return result;
	}
	//회원탈퇴
	@Transactional
	public int deleteMember(MemberDTO member) {
		int result = memberDao.deleteMember(member);
		int result2 = memberDao.insertDelMember(member);
		return result+=result2;
	}

	//관리자페이지 경고회원 조회
	public List<MemberDTO> getWarningMembers() {
		List<MemberDTO> list = memberDao.selectWarningMembers();
		System.out.println(list);
		return list;
	}

	public void updateMemberLevel(int memberNo, int memberLevel) {
		memberDao.updateMemberLevel(memberNo, memberLevel);
	}

	public void kickMember(int memberNo) {
		memberDao.insertDelMember(memberNo);
		
	}




	
}
