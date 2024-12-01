package com.meta.sys.user.controller.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.sys.user.controller.request.SysUserRequest;
import com.meta.usr.user.domain.User;

public interface SysUserService {

    Page<User> list(SysUserRequest requestDto, Pageable pageable);
    
}
