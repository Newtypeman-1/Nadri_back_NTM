package kr.co.iei.review.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="likes")
public class LikeDTO {
private String memberNickname;
private int reviewNo;
}
