package kr.co.iei.plan.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="itineraryWithPlace")
public class ItineraryWithPlaceDTO {
	//plan_itinerary
	private String itineraryDate;
	private String transport;
	private int itineraryOrder;

	//place_info
	private int placeId;
	private String placeTitle;
	private String placeAddr;
	private int placeTypeId;
	private double mapLat;
	private double mapLng;
	private String placeThumb;
	//review
	private double placeRating;
	private int placeReview;
}
