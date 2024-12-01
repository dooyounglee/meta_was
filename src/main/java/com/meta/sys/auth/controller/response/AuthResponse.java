package com.meta.sys.auth.controller.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.meta.cmm.util.DateUtil;
import com.meta.sys.auth.domain.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthResponse {
	
	/** 권한ID */
    private long authId;
    
    /** 권한명 */
    private String authCd;

    /** 권한명 */
    private String authNm;
    
    /** 사용여부 */
    private String useYn;
    
    private Long createdBy;
    
    private String createdYmd;

    @Builder.Default
    public List<AuthMenuResponse> menuList = new ArrayList<>();

    @Builder.Default
    public List<AuthUserResponse> userList = new ArrayList<>();
    
    
    /**
    * @methodName    : toDto
    * @author        : KIMHYEOKJIN
    * @description   : Entity -> Dto 
    * @param auth
    * @return AuthResDto
    */
    public static AuthResponse to(Auth auth) {
    	return AuthResponse.builder()
                .authId(auth.getAuthId())
                .authCd(auth.getAuthCd())
                .authNm(auth.getAuthNm())
                .useYn(auth.getUseYn())
                .createdYmd(DateUtil.format(auth.getCreatedDate(), "yyyy-MM-dd"))
    			.build();
    }

    public static AuthResponse toDetail(Auth auth) {
    	return AuthResponse.builder()
                .authId(auth.getAuthId())
                .authCd(auth.getAuthCd())
                .authNm(auth.getAuthNm())
                .useYn(auth.getUseYn())
                .menuList(auth.getMenuList() == null ? null : auth.getMenuList().stream().map(authMenu -> AuthMenuResponse.toDto(authMenu)).collect(Collectors.toList()))
                .userList(auth.getUserList() == null ? null : auth.getUserList().stream().map(authUser -> AuthUserResponse.toDto(authUser)).collect(Collectors.toList()))
                .createdYmd(DateUtil.format(auth.getCreatedDate(), "yyyy-MM-dd"))
    			.build();
    }
}
