package kr.co.iei.member.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.member.model.dto.LoginMemberDTO;
import kr.co.iei.member.model.dto.MemberDTO;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.util.FileUtils;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private FileUtils fileUtils;
	@Value("${file.root}")
	private String root;
	
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
	
	//소셜회원가입
	@PostMapping(value="/socialJoin")
	private ResponseEntity<Integer> socialJoin(@RequestBody MemberDTO member){
		int result = memberService.socialJoin(member);
		return ResponseEntity.ok(result);
	}
	
	//비밀번호 재설정
	@PatchMapping(value="/updatePw")
	public ResponseEntity<Integer> updatePw(@RequestBody MemberDTO member){
		int result = memberService.updatePw(member);
		return ResponseEntity.ok(result);
	}
	
	//로그인
	@PostMapping(value="/login")
	public ResponseEntity<LoginMemberDTO> login(@RequestBody MemberDTO member){
	    // 로그인 시도
	    LoginMemberDTO loginMember = memberService.login(member);
	    // 로그인 실패 시 바로 404 응답
	    if (loginMember == null) {
	        return ResponseEntity.status(404).build();  // 로그인 실패
	    }

	    // 강제 탈퇴 여부 확인 (로그인 성공 후 확인)
	    Integer deleteMember = memberService.loginIsDel(member);

	    if (deleteMember != null && deleteMember.intValue() == 2) {
	        return null;  // 강제 탈퇴된 경우 null 반환 (응답 본문 없음)
	    }

	    // 로그인 성공한 경우
	    return ResponseEntity.ok(loginMember);  // 로그인 성공
	}
	
	//소셜로그인
	@GetMapping(value="/socialLogin")
	public ResponseEntity<LoginMemberDTO> socialLogin(@RequestParam String userEmail){
		LoginMemberDTO loginMember = memberService.socialLogin(userEmail);
		if(loginMember != null) {
			return ResponseEntity.ok(loginMember);
		}else {
			return ResponseEntity.status(404).build();
		}
	}
	
	//소셜 이메일 확인
    @GetMapping(value="/isSocial")
    public ResponseEntity<Integer> isSocial(@RequestParam String email) {
    	int result = memberService.isSocial(email);
    	return ResponseEntity.ok(result);
    }
	
	//마이페이지 회원정보
	@GetMapping(value="/memberInfo")
	public ResponseEntity<MemberDTO> selectMemberInfo(@RequestParam String memberNickname){
		MemberDTO member = memberService.selectMemberInfo(memberNickname);
		return ResponseEntity.ok(member);
	}
	
	//마이페이지 회원정보 수정
	@PatchMapping
	public ResponseEntity<Integer> updateMember(@ModelAttribute MemberDTO member, @ModelAttribute MultipartFile uploadProfile){
		int result = 0;
		if(uploadProfile != null) {
			String savepath = root +"/profile/";
			String filepath = fileUtils.upload(savepath, uploadProfile);
			member.setProfileImg(filepath);
			String delfile = memberService.updateMemberNewFile(member);
			if(delfile != null) {
				File file = new File(root+"/profile/"+delfile);
				if(file.exists()) {
					file.delete();
				}
			}
		}else {
			if(member.getProfileImg() != null) {
				//1. 기존 프로필 이미지 유지
				result = memberService.updateMemberPresFile(member);
			}else{	
				//2. 기본으로 변경  -> 기존 파일이 있으면 삭제
				String filepath2 = memberService.updateMemberDelFile(member);
				//기본 프로필로 돌린 경우
				if(filepath2 != null) {
					File file = new File(root+"/profile/"+filepath2);
					if(file.exists()) {
						file.delete();
					}
				}
			}	
		}
		return ResponseEntity.ok(result);
	}
	
	//회원탈퇴
	@PatchMapping(value="/deleteMember")
	public ResponseEntity<Integer> deleteMember(@RequestBody MemberDTO member){
		int result = memberService.deleteMember(member);
		return ResponseEntity.ok(result);
	}
	//탈퇴된 회원탈퇴
	@PatchMapping(value="/deleteDelMember")
	public ResponseEntity<Integer> deleteDelMember(@RequestBody MemberDTO member){
		int result = memberService.deleteDelMember(member);
		return ResponseEntity.ok(result);
	}
}
