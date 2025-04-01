package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.member.model.dto.MemberDTO;
import kr.co.iei.member.model.service.MemberService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	//이메일 중복검사
	@GetMapping(value="/existsEmail")
	public ResponseEntity<Integer> existsEmail(@RequestParam String memberEmail){
		int result = memberService.existsEmail(memberEmail);
		return ResponseEntity.ok(result);	
	}
	
	//닉네임 중복검사
	@GetMapping(value="/exists")
	public ResponseEntity<Integer> exists(@RequestParam String memberNickname){
		int result = memberService.exists(memberNickname);
		return ResponseEntity.ok(result);	
	}
	
	//회원가입
	@PostMapping(value="/join")
	private ResponseEntity<Integer> join(@RequestBody MemberDTO member){
		int result = memberService.insertMember(member);
		return ResponseEntity.ok(result);
	}
	
	//비밀번호 재설정
	@PatchMapping(value="/updatePw")
	public ResponseEntity<Integer> updatePw(@RequestBody MemberDTO member){
		System.out.println(member);
		int result = memberService.updatePw(member);
		return ResponseEntity.ok(result);
	}
	
	//로그인
	@PostMapping(value="/login")
	public ResponseEntity<LoginMemberDTO> login(@RequestBody MemberDTO member){
		LoginMemberDTO loginMember = memberService.login(member);
		if(loginMember != null) {
			return ResponseEntity.ok(loginMember);
		}else {
			return ResponseEntity.status(404).build();
		}
	}
	
	//마이페이지 회원정보
	@GetMapping(value="/selectMember")
	public ResponseEntity<MemberDTO> selectMember(@RequestParam String memberNickname){
		MemberDTO member = memberService.selectMember(memberNickname);
		return ResponseEntity.ok(member);
	}
}
