package kr.co.iei.tour.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="commonContent")
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
	private String areaName;
	private String sigunguName;
	private String cat3Name;
}
