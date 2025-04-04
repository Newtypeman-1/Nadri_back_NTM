package kr.co.iei.place.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="spot")
public class SpotDTO {
	private int placeId;
	private int heritage1;
	private int heritage2;
	private int heritage3;
	private String useSeason;
}
