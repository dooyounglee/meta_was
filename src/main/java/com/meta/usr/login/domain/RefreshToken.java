package com.meta.usr.login.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {
    
    private long tknId;
    private Long usrNo;
    private String refreshToken;
}
