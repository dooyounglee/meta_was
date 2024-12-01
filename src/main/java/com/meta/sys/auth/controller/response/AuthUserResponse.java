package com.meta.sys.auth.controller.response;

import com.meta.sys.auth.domain.AuthUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthUserResponse {
    
    private long usrNo;
    private String usrNm;

    public static AuthUserResponse toDto(AuthUser entity) {
        return AuthUserResponse.builder()
            .usrNo(entity.getUser().getUsrNo())
            .usrNm(entity.getUser().getUsrNm())
            .build();
    }
}
