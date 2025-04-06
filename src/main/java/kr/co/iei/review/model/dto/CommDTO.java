package kr.co.iei.review.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="comm")
public class CommDTO {	
private int commNo;
private String memberNickname;
private String commContent;
private int reviewNo;
}
