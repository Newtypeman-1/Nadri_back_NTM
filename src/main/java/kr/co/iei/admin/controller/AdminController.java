package kr.co.iei.admin.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.admin.model.dto.CompanyDTO;
import kr.co.iei.admin.model.dto.EventDTO;
import kr.co.iei.admin.model.dto.KeywordDTO;
import kr.co.iei.admin.model.service.AdminService;
import kr.co.iei.member.model.dto.MemberDTO;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.PlaceUpdateRequestDTO;
import kr.co.iei.place.model.service.PlaceService;
import kr.co.iei.review.model.dto.ReportDTO;
import kr.co.iei.review.model.dto.ReviewDTO;
import kr.co.iei.review.model.service.ReviewService;
import kr.co.iei.util.FileUtils;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private AdminService adminService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PlaceService placeService;
	@GetMapping("/company")
	private ResponseEntity<CompanyDTO> selectCompanyInfo(){
		CompanyDTO company = adminService.selectCompanyInfo();
		return ResponseEntity.ok(company); 
	}
	@PatchMapping("/company")
	private ResponseEntity<Integer> updateCompany(@RequestBody CompanyDTO company){
		int result = adminService.updateCompanyInfo(company);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/event/{month}")
	public ResponseEntity<List> selectMonthEvent(@PathVariable String month){
		List eventList = adminService.selectMonthEvent(month);
		return ResponseEntity.ok(eventList);
	}
	@GetMapping("/event/onGoing")
	public ResponseEntity<List> selectOnGoingEvent(@RequestParam String date){
		List eventList = adminService.selectOnGoingEvent(date);
		return ResponseEntity.ok(eventList);
	}
	@GetMapping("/event/end")
	public ResponseEntity<List> selectEndEvent(@RequestParam String date){
		List eventList = adminService.selectEndEvent(date);
		return ResponseEntity.ok(eventList);
	}
	
	@PostMapping("/event")
	public ResponseEntity<Integer> insertEvent(@ModelAttribute EventDTO event,@ModelAttribute MultipartFile img){
		String savepath = root +"/event/thumb/";
		String filepath = fileUtils.upload(savepath, img);
		event.setEventImg(filepath);
		int result = adminService.insertEvent(event);
		return ResponseEntity.ok(result);
	}
	@PatchMapping("/event/{eventNo}")
	public ResponseEntity<Integer> updateEvent(@ModelAttribute EventDTO event,@ModelAttribute MultipartFile img,@PathVariable int eventNo){
		if(img!=null) {
			String savepath = root +"/event/thumb/";
			String filepath = fileUtils.upload(savepath, img);
			event.setEventImg(filepath);
		}
		String filepath = adminService.updateEvent(event);
		if(filepath!=null){
			File file = new File(root+"/event/thumb/"+filepath);
			if(file.exists()) {
				file.delete();
			}
		}
		return ResponseEntity.ok(1);
	}
	@DeleteMapping("/event/{eventNo}")
	public ResponseEntity<Integer> deleteEvent(@PathVariable int eventNo){
		String filepath = adminService.deleteEvent(eventNo);
		File file = new File(root+"/event/thumb/"+filepath);
		if(file.exists()) {
			file.delete();
		}
		return ResponseEntity.ok(1);
	}
	@GetMapping("/report")
	public ResponseEntity<List> reportedReview(@RequestParam int status){
		List reportedReviews = reviewService.selectReportedReview(status);
		return ResponseEntity.ok(reportedReviews);
	}
	@GetMapping("/keyword/{keyword}")
	public ResponseEntity<KeywordDTO> selectKeywordInfo(@PathVariable String keyword){
		KeywordDTO keywordInfo = adminService.selectKeywordInfo(keyword);
		return ResponseEntity.ok(keywordInfo);
	}
	@PatchMapping("/keyword")
	public ResponseEntity<Integer> upsertKeywordInfo(@RequestBody KeywordDTO keyword){
		int result = adminService.upsertKeywordInfo(keyword);
		return ResponseEntity.ok(result);
	}
	@PatchMapping("/report")
	public ResponseEntity<Integer> updateReport(@RequestBody ReportDTO report){
		int result = adminService.updateReport(report);
		return ResponseEntity.ok(result);
	}
	
	//경고멤버 조회
	@GetMapping("/member/list")
	public ResponseEntity<List<MemberDTO>> getMemberList(@RequestParam int status) {
	    return ResponseEntity.ok(memberService.getMembersByStatus(status));
	}
	
    //레벨등급 조정
    @PatchMapping("/member/level")
    public ResponseEntity<Integer> updateMemberLevel(@RequestBody Map<String, Object> param) {
        int memberNo = (int) param.get("memberNo");
        int memberLevel = (int) param.get("memberLevel");
        memberService.updateMemberLevel(memberNo, memberLevel);
        return ResponseEntity.ok().build();
    }
    //경고멤버 강퇴(탈퇴회원 테이블에 추가 : 최종 delete는 member에서 수행)
    @PatchMapping("/member/{memberNo}")
    public ResponseEntity<Integer> kickWarningMember(@PathVariable int memberNo) {
        memberService.insertDelWarningMember(memberNo);
        return ResponseEntity.ok().build();
    }
    
    //플레이스 상세페이지 사진 삭제
    @DeleteMapping("/place/image/{placeImageNo}")
    public ResponseEntity<Void> deletePlaceImage(@PathVariable int placeImageNo) {
        placeService.deleteByImageNo(placeImageNo);
        return ResponseEntity.ok().build();
    }
    
    //플레이스 상세페이지 수정(관리자 계정 상세페이지)
    @PatchMapping("/place/update")
    public ResponseEntity<Integer> updatePlace(@RequestBody PlaceInfoDTO placeInfoDTO) {
    	int result = placeService.updatePlace(placeInfoDTO);
    	return ResponseEntity.ok(result);
    }
    
    //사용자 상세페이지 수정 요청
    @PostMapping("/place/request")
    public ResponseEntity<String> requestUpdate(@RequestBody PlaceUpdateRequestDTO dto) {
        placeService.saveRequest(dto);
        return ResponseEntity.ok("요청이 접수되었습니다");
    }
    
    //사용자 요청 셀렉트
    @GetMapping("/place/request")
    public ResponseEntity<List<PlaceUpdateRequestDTO>> getRequests(@RequestParam(required = false) Integer status) {
        List<PlaceUpdateRequestDTO> list = placeService.selectRequestsByStatus(status);
        return ResponseEntity.ok(list);
    }

    //관리자 정보수정 및 기록
    @PatchMapping("/place/update/request")
    public ResponseEntity<String> updatePlace(@RequestBody PlaceUpdateRequestDTO dto) {
    	System.out.println(dto);
        placeService.updateFromRequest(dto);
        return ResponseEntity.ok("장소 정보가 수정되었습니다");

    }
}
