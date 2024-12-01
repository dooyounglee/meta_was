package com.meta.sys.auth.controller.response;

import com.meta.sys.auth.domain.AuthMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthMenuResponse {
    
    private long menuId;
    private String menuNm;
    private Long parentId;
    private long menuOrder;

    public static AuthMenuResponse toDto(AuthMenu entity) {
        return AuthMenuResponse.builder()
            .menuId(entity.getMenu().getMenuId())
            .menuNm(entity.getMenu().getMenuNm())
            .build();
    }
}
