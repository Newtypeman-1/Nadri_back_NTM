package kr.co.iei.chat.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("chatMember")
public class ChatMemberDTO {
    private String memberNickname;
    private String profileImg;
}
