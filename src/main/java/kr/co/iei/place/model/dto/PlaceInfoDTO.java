	package kr.co.iei.place.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="place")
public class PlaceInfoDTO {
	private String placeThumb;
	private double mapLat;
	private double mapLng;
	private String areaCode;
	private String sigunguCode;
	private String placeCat1;
	private String placeCat2;
	private String placeCat3;
	private String placeTel;
	private String placeTitle;
	private String placeAddr;
	private int placeId;
	private int placeTypeId;
	
	private String placeOverview;
	
	private String useTime;
	private String restDate;
	private String parking;
	
	//PlaceInfo 테이블에 없음(테이블 조인)
	private String areaName;
	private String sigunguName;
	private String cat3Name;
	private String cat2Name;
	
	//PlaceInfo 테이블에 없음
	private float placeRating; //장소에 대한 평점
	private int placeReview; //장소에 대한 리뷰 수
	private double distance; //마커로부터 장소까지의 거리(m)
	
	//즐겨찾기 할 닉네임
	private String memberNickname;
	//방문 횟수
	private int visitCount;
}
