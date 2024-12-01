package com.meta.sys.auth.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdateSetting;
import com.meta.sys.menu.domain.Menu;
import com.meta.usr.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Auth {
    
    private Long authId;
    private String authCd;
    private String authNm;
    private String useYn;
    private List<AuthMenu> menuList;
    private List<AuthUser> userList;

    private LocalDateTime createdDate;

    public void changeAuth(AuthUpdate dto) {
        this.authCd = dto.getAuthCd();
    	this.authNm = dto.getAuthNm();
    	this.useYn = dto.getUseYn();

        // 권한 삭제하면 권한에 꽂았던 사용자목록 초기화
        if ("N".equals(dto.getUseYn())) {
            this.userList = new ArrayList<>();
        }
    }

    public void changeAuthSetting(AuthUpdateSetting dto) {

        this.menuList = dto.getMenuList().stream().map(authMenu -> AuthMenu.builder()
            .auth(this)
            .menu(Menu.builder().menuId(authMenu.getMenuId()).build()).build()).collect(Collectors.toList());
    	this.userList = dto.getUserList().stream().map(authUser -> AuthUser.builder()
            .auth(this)
            .user(User.builder().usrNo(authUser.getUsrNo()).build())
            .build()).collect(Collectors.toList());
    }
}
