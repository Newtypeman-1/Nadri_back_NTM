package kr.co.iei.admin.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("keyword")
public class KeywordDTO {
	private String keyword;
	private Integer type;
	private String cat1;
	private String cat2;
	private String cat3;
	private Integer placeId;
	private Integer area;
}
