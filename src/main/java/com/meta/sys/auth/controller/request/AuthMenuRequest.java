package com.meta.sys.auth.controller.request;

import javax.validation.constraints.NotNull;

import com.meta.sys.auth.domain.AuthMenu;
import com.meta.sys.menu.domain.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class AuthMenuRequest {
    
    @Data
	@Builder
    @NoArgsConstructor
    @AllArgsConstructor
	public static class AuthMenuCreate {
		/** 메뉴ID */
		@NotNull(message = "메뉴ID는 필수값 입니다.")
		private Long menuId;

        public AuthMenu to() {
            return AuthMenu.builder()
                    .menu(Menu.builder().menuId(this.menuId).build())
                    .build();
        }
	}
}
