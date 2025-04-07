package kr.co.iei.plan.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="itinerary")
public class ItineraryDTO {
	private int itineraryNo;
	private int planNo;
	private String itineraryDate;
	private int startLocation;
	private String transport;
	private int endLocation;
	private int itineraryOrder;
}
