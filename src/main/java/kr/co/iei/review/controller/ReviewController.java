package kr.co.iei.review.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.review.model.dto.CommDTO;
import kr.co.iei.review.model.dto.PlaceImgDTO;
import kr.co.iei.review.model.dto.ReportDTO;
import kr.co.iei.review.model.dto.ReviewDTO;
import kr.co.iei.review.model.service.ReviewService;
import kr.co.iei.util.FileUtils;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private FileUtils fileUtils;
	@Value("${file.root}")
	private String root;
	@GetMapping
	public ResponseEntity<Map> reviewlist(@RequestParam int reqPage, @RequestParam(required = false) 
	String type, @RequestParam(required = false)int[] id){
		Map map = reviewService.reviewList(reqPage,type,id);
		return ResponseEntity.ok(map);
	}
	@GetMapping(value="/{reviewNo}")
	public ResponseEntity<ReviewDTO> selectOneReview(@PathVariable int reviewNo){
		ReviewDTO review =reviewService.selectOneReview(reviewNo);
		return ResponseEntity.ok(review);
	}
	@DeleteMapping(value="/{reviewNo}")
	public ResponseEntity<Integer>deleteReview(@PathVariable int reviewNo){
		int result = reviewService.deleteReview(reviewNo);
		return ResponseEntity.ok(result);
	}
		
	@GetMapping("/stats")
	public ResponseEntity<List> reviewStats(){
		List<AdminStatsDTO> reviewStats= reviewService.selectReviewStats();
		return ResponseEntity.ok(reviewStats);
	}
	@GetMapping("/hotReview")
	public ResponseEntity<List> hotReview(@RequestParam int type){
		List<ReviewDTO> hotReviews= reviewService.selectHotReview(type);
		return ResponseEntity.ok(hotReviews);
	}
		
	@GetMapping(value="/detail/{placeId}")
	public ResponseEntity<List> oneReviewlist(@PathVariable int placeId){
	
		List list = reviewService.oneReviewList(placeId);
		return ResponseEntity.ok(list);
	}

	@PostMapping
	public ResponseEntity<Integer> insertReview(@ModelAttribute ReviewDTO review,  @ModelAttribute  MultipartFile[] files){
		List<PlaceImgDTO> placeImgList = new ArrayList<>();
		if(files != null) {
			String savepath = root+"/place/image/";
			for(MultipartFile file : files ) {
				PlaceImgDTO placeImg = new PlaceImgDTO();
				String filepath = fileUtils.upload(savepath, file);
				placeImg.setFilepath(filepath);
				placeImgList.add(placeImg);
			}
			
		}
		int result= reviewService.insertReview(review,placeImgList);
		return ResponseEntity.ok(result);
	}
	@GetMapping("/reviewImage")
    public ResponseEntity<List<PlaceImgDTO>> getReviewImages(@RequestParam int reviewNo) {
       List<PlaceImgDTO> list = reviewService.searchImg(reviewNo);
   		return ResponseEntity.ok(list);
    }
	@PatchMapping
	public ResponseEntity<Integer> updateReview(@ModelAttribute ReviewDTO reviewDTO ,
			  @RequestParam(value = "deleteFilepaths", required = false) List<String> deleteFilepaths,
			    @RequestParam(value = "multipartFile", required = false) MultipartFile[] files){
		System.out.println(reviewDTO);
		System.out.println(files);
		System.out.println(deleteFilepaths);
		List<PlaceImgDTO> placeImgList = new ArrayList<>();
		if(files != null) {
			String savepath = root+"/place/image/";
			for(MultipartFile file : files ) {
				PlaceImgDTO placeImg = new PlaceImgDTO();
				String filepath = fileUtils.upload(savepath, file);
				placeImg.setFilepath(filepath);
				placeImgList.add(placeImg);
			}
		}
		int result = reviewService.updateReview(reviewDTO,placeImgList,deleteFilepaths);
		return ResponseEntity.ok(result);
	}
	@GetMapping(value="/likes/{reviewNo}")
	public ResponseEntity<Map> reviewLike(@PathVariable int reviewNo){
		Map map = reviewService.reviewLike(reviewNo);
		return ResponseEntity.ok(map);
	}
	@PostMapping(value="/likes")
    public ResponseEntity<Integer> addLike(
            @RequestParam("reviewNo") int reviewNo,
            @RequestParam("memberNickname") String memberNickname) {

        int result = reviewService.addLike(reviewNo, memberNickname);
        return ResponseEntity.ok(result); // 성공 시 1, 실패 시 0
    }   
	@DeleteMapping("/likes/{reviewNo}")
    public ResponseEntity<Integer> removeLike(
            @PathVariable int reviewNo,
            @RequestBody Map<String, String> body) {

        String memberNickname = body.get("memberNickname");
        int result = reviewService.removeLike(reviewNo, memberNickname);
        return ResponseEntity.ok(result); // 성공 시 1, 실패 시 0
    }
	@PostMapping(value = "/report/")
	public ResponseEntity<Integer> insertreport(@RequestBody ReportDTO reportDTO){
	int result =reviewService.insertReport(reportDTO);
		 return ResponseEntity.ok(result);
	}
	@GetMapping(value="/report/{reviewNo}")
	public ResponseEntity<List> reportList(@PathVariable int reviewNo){
		List list= reviewService.reportList(reviewNo);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value="/comm/{reviewNo}")
	public ResponseEntity<List> commList(@PathVariable int reviewNo){
	List list = reviewService.commList(reviewNo);
		 return ResponseEntity.ok(list);
	}
	@DeleteMapping(value="/comm/{commNo}")
	public ResponseEntity<Integer> deleteComm(@PathVariable int commNo){
		int result = reviewService.deleteComm(commNo);
		 return ResponseEntity.ok(result);
	}
	@PostMapping(value="/comm")
	public ResponseEntity<CommDTO> insertComm(@ModelAttribute CommDTO comm ){
		CommDTO comment = reviewService.insertComm(comm);
		return ResponseEntity.ok(comment);
	}
	@PatchMapping("/comm/{commNo}")
	public ResponseEntity<Integer> patchComment(@PathVariable int commNo,
			@RequestBody Map<String, String> newComment) {
		String commContent= newComment.get("commContent");
		 CommDTO commDTO= new CommDTO();
		  commDTO.setCommContent(commContent);
		commDTO.setCommNo(commNo);
		int result= reviewService.patchComment(commDTO);
		return ResponseEntity.ok(result);
	
}
}
