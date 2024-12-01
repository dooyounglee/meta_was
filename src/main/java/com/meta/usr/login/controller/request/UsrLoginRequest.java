package com.meta.usr.login.controller.request;

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
    }
}
