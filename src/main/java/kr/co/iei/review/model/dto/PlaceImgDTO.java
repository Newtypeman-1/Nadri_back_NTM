package kr.co.iei.review.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="img")
public class PlaceImgDTO {
private int placeImageNo; //placeImgNo아님
private int placeId;
private String filepath;
private int reviewNo;
}
