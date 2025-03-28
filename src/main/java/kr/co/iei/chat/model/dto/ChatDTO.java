package kr.co.iei.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatDTO {
	private String type;
	private int chatNo;
	private String message;
}
