package kr.co.iei.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContentCommonDTO {
	private String contentThumb;
	private double mapLat;
	private double mapLng;
	private String areaCode;
	private String sigunguCode;
	private String contentCat1;
	private String contentCat2;
	private String contentCat3;
	private String contentTel;
	private String contentTitle;
	private String contentAddr;
	private int contentId;
	private int contentTypeId;
}
