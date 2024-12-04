package com.meta.usr.login.controller.request;

import com.meta.usr.user.domain.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsrLoginRequest {
    
    @Data
    @Builder
    public static class UsrLoginLogin {
        private String usrId; // 사용자ID
        private String usrPw; // 사용자비번

        public User to() {
            return User.builder()
                .usrId(usrId)
                .usrPw(usrPw)
                .build();
        }

        public User to(String encodedPw) {
            return User.builder()
                .usrId(usrId)
                .usrPw(encodedPw)
                .build();
        }
    }
}
