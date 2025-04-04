package kr.co.iei.place.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="placeInfo")
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
	
	private String useTime;
	private String restDate;
	private String parking;
	
	private String areaName;
	private String sigunguName;
	private String cat3Name;
}
