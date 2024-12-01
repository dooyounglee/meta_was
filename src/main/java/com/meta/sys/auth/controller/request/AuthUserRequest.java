package com.meta.sys.auth.controller.request;

import javax.validation.constraints.NotNull;

import com.meta.sys.auth.domain.AuthUser;
import com.meta.usr.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class AuthUserRequest {
    
    @Data
	@Builder
    @NoArgsConstructor
    @AllArgsConstructor
	public static class AuthUserCreate {
		/** 사용자번호 */
		@NotNull(message = "사용자번호는 필수값 입니다.")
		private Long usrNo;

        public AuthUser toEntity() {
            return AuthUser.builder()
                    .user(User.builder().usrNo(usrNo).build())
                    .build();
        }
	}
}
