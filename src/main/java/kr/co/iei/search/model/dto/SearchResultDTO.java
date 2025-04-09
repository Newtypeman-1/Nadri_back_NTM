package kr.co.iei.search.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("search")
public class SearchResultDTO {
	private int placeTypeId;
	private String placeTitle;
	private String placeAddr;
	private String area;
	private String sigungu;
	private String placeThumb;
}
