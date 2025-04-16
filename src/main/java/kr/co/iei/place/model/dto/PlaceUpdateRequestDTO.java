package kr.co.iei.place.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="updateRequest")
public class PlaceUpdateRequestDTO {
    private int requestNo;
    private int placeId;
    private String placeTitle;
    private String placeAddr;
    private String placeTel;
    private String requestDate;
    private String isChecked;
	

}
