package kr.co.iei.place.model.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="req")
public class PlaceFilterRequest {
    private int selectedMenu;             // 메뉴 ID (1~4)
    private List<String> filters;         // 체크된 필터들 (한글 이름)
    private int reqPage;                  // 요청 페이지 번호
    private int order;                    // 정렬 방식 (1: 리뷰순, 2: 별점순, 3: 좋아요순)
    private String memberNickname;        // 로그인 유저 닉네임 (필수X)

}
