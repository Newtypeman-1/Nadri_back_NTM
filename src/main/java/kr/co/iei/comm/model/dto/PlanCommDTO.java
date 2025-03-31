package kr.co.iei.comm.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="plancomm")
public class PlanCommDTO {
	private int PlanCommNo;
	private int reviewNo;
	private String commContent;
}
