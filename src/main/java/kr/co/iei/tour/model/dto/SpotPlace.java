package kr.co.iei.tour.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="spot")
public class SpotPlace {
	private int placeId;
	private int placeTypeId;
	private String placeTitle;
	private String placeAddr;
	private String placeTel;
	private String areaCode;
	private String sigunguCode;
	private String placeOverview;
	private String placeCat1;
	private String placeCat2;
	private String placeCat3;
	private double mapLat;
	private double mapLng;
	private String placeThumb;
	private String usetime;
	private String restdate;
	private String parking;
	
	//여기서부터는 content 컬럼에 없습니다
	private int placeRating; //장소에 대한 평점
	private int placeReview; //장소에 대한 리뷰 수
	private double distance; //마커로부터 장소까지의 거리(m)
}
