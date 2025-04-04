package kr.co.iei.plan.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="group")
public class PlanGroupDTO {
	private int planNo;
	private String memberNickname;
}
