package kr.co.iei.review.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="report")
public class ReportDTO {
	private int reportNo;
	private int reviewNo;
	private String reviewTitle;
	private String memberNickname;
	private String reportNickname;
	private String reportReason;
	private String reviewDate;
	private int reportStatus;
}
