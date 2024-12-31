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

    @Data
    @Builder
    public static class UsrLoginSignup {
        private String usrId;
        private String usrNm;
        private String usrComp;
        private String usrPos;
        private String usrPhone;

        public User to() {
            return User.builder()
                .usrId(usrId)
                .usrNm(usrNm)
                .usrComp(usrComp)
                .usrPos(usrPos)
                .usrPhone(usrPhone)
                .build();
        }
    }

    @Data
    @Builder
    public static class UsrLoginReset {
        private long usrNo;

        public User to() {
            return User.builder()
                .usrNo(usrNo)
                .build();
        }
    }
}
