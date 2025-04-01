package kr.co.iei.chat.model.dto;
import java.util.List;

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
	private String memberNickname;
	private List<ChatMemberDTO> groupInfo;
	private int notRead;
	private int latestContentNo; 
}
