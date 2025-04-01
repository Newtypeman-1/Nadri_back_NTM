package kr.co.iei.plan.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="plan")
public class PlanDTO {
	private int planNo;
	private String planName;
	private String startDate;
	private String endDate;
	private String planThumb;
	private int planStatus;
}
