package com.meta.sys.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.sys.user.controller.port.SysUserService;
import com.meta.sys.user.controller.request.SysUserRequest;
import com.meta.sys.user.controller.response.SysUserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController {
    
    private final SysUserService sysUserService;

    @GetMapping(value = "/list")
    public Page<SysUserResponse> list(SysUserRequest requestDto, @PageableDefault(size = 10 ,page = 0) Pageable pageable) {
        return sysUserService.list(requestDto, pageable)
            .map(SysUserResponse::from);
    }
}
