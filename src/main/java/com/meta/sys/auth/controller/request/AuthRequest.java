package com.meta.sys.auth.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.meta.sys.auth.controller.request.AuthMenuRequest.AuthMenuCreate;
import com.meta.sys.auth.controller.request.AuthUserRequest.AuthUserCreate;
import com.meta.sys.auth.domain.Auth;
import com.meta.sys.auth.domain.AuthMenu;
import com.meta.sys.auth.domain.AuthUser;
import com.meta.sys.menu.domain.Menu;
import com.meta.usr.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthRequest {
	
	@Data
	@Builder
	public static class AuthCreate {
		/** 권한ID */
		// @NotNull (message = "권한ID는 필수값 입니다.")
	    private Long authId;
		
		/** 권한코드 */
		@NotNull(message = "권한코드은 필수값 입니다.")
	    private String authCd;

		/** 권한명 */
		@NotNull(message = "권한명은 필수값 입니다.")
	    private String authNm;
		
		/** 사용여부 */
		@NotNull(message = "사용여부는 필수값 입니다.")
	    private String useYn;
		
		public Auth toEntity() {
			return Auth.builder()
					.authId(authId)
					.authCd(authCd)
					.authNm(authNm)
					.useYn(useYn)
					.build();
		}
	}
	
	@Data
	@Builder
	public static class AuthUpdate {
		/** 권한ID */
		@NotNull (message = "권한ID는 필수값 입니다.")
	    private Long authId;

		/** 권한코드 */
		@NotNull(message = "권한코드은 필수값 입니다.")
	    private String authCd;

		/** 권한명 */
		@NotNull(message = "권한명은 필수값 입니다.")
	    private String authNm;
		
		/** 사용여부 */
		@NotNull(message = "사용여부는 필수값 입니다.")
	    private String useYn;
		
		/** 메뉴목록 */
	    private List<AuthMenuCreate> menuList;

		/** 사용자목록 */
	    private List<AuthUserCreate> userList;
		
		public Auth toEntity() {
			return Auth.builder()
					.menuList(menuList.stream().map(menu -> AuthMenu.builder().menu(Menu.builder().menuId(menu.getMenuId()).build()).build()).collect(Collectors.toList()))
					.userList(userList.stream().map(user -> AuthUser.builder().user(User.builder().usrNo(user.getUsrNo()).build()).build()).collect(Collectors.toList()))
					.build();
		}
	}
	
	@Data
	public static class AuthUpdateSetting {
		/** 권한ID */
		@NotNull (message = "권한ID는 필수값 입니다.")
	    private Long authId;

		/** 메뉴목록 */
	    private List<AuthMenuCreate> menuList = new ArrayList<>();

		/** 사용자목록 */
	    private List<AuthUserCreate> userList = new ArrayList<>();
		
		public Auth toEntity() {
			return Auth.builder()
					.menuList(new ArrayList<>())
					.userList(new ArrayList<>())
					.build();
		}
	}

	@Data
	@Builder
	@AllArgsConstructor
	public static class AuthSetting {
		/** 권한ID */
		@NotNull (message = "권한ID는 필수값 입니다.")
	    private Long authId;
		
		public Auth toEntity() {
			return Auth.builder()
					.authId(authId)
					.build();
		}
	}
	
}
