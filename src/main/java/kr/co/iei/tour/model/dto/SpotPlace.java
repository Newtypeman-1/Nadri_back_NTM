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
	private int contentId;
	private int contentTypeId;
	private String contentTitle;
	private String contentAddr;
	private String contentTel;
	private String areaCode;
	private String sigunguCode;
	private String contentOverview;
	private String contentCat1;
	private String contentCat2;
	private String contentCat3;
	private double mapLat;
	private double mapLng;
	private String contentThumb;
	private String usetime;
	private String restdate;
	private String parking;
}
