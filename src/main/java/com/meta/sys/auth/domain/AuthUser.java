package com.meta.sys.auth.domain;

import com.meta.usr.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthUser {
    
    private Long authUserId;
    private Auth auth;
    private User user;
}
