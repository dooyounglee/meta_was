package com.meta.sys.auth.domain;

import com.meta.sys.menu.domain.Menu;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthMenu {
    
    private Long authMenuId;
    private Auth auth;
    private Menu menu;
}
