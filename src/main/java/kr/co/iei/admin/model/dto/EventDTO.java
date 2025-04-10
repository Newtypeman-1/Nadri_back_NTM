package kr.co.iei.admin.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("event")
public class EventDTO {
	private int eventNo;
	private int placeTypeId;
	private String eventTitle;
	private String eventContent;
	private String startDate;
	private String endDate;
	private String eventImg;
}
