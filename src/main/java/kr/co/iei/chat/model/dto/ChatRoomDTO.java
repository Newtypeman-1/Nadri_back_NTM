package kr.co.iei.chat.model.dto;
import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("chatRoom")
public class ChatRoomDTO {
	private int chatNo;
	private String chatTitle;
	private String groupSize;
	private int notRead; 
}
