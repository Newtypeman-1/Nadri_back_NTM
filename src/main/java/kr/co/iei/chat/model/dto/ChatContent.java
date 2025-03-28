package kr.co.iei.chat.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("chatContent")
public class ChatContent {
	private String MemberNickname;
	private String chatImg;
	private String chatContent;
}
