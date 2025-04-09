package kr.co.iei.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoUser {
    private String email; 
    
    public KakaoUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
