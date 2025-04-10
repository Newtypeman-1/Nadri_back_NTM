package kr.co.iei.admin.model.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("stats")
public class AdminStatsDTO {
	private int placeTypeId;
	private int reviewCount;
	private int planCount;
	private int[] mostPlace;
 }
