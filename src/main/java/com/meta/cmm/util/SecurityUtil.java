package com.meta.cmm.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.meta.cmm.exception.BusinessException;
import com.meta.usr.user.domain.User;

public class SecurityUtil {
    
    public static User getUser() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return (User) authentication.getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("SYS-020"); // 잘못된 로그인 정보 입니다.
        }
    }
}
