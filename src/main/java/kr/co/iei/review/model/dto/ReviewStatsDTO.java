package kr.co.iei.review.model.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("stats")
public class ReviewStatsDTO {
	private int placeTypeId;
	private int reviewCount;
}
