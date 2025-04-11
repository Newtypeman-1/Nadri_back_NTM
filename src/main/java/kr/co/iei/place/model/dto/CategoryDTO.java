package kr.co.iei.place.model.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("category")
public class CategoryDTO {
	@JsonIgnore
	private int catIndex;
	private String id;
	private String name;
}
