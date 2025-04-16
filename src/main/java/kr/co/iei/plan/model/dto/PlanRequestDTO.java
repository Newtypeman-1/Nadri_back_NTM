package kr.co.iei.plan.model.dto;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanRequestDTO {
	private int reqPage;
	private Integer numPerPage;
	private String loginNickname; // 현재 로그인 유저
	private String memberNickname; // 내가 작성한 플랜을 볼 때 사용
	private int[] id;
	private Integer order;
	private Integer start;
	private Integer end;
	private Boolean isBookmark; // 즐겨찾기한 플랜만 필터링 여부
}
