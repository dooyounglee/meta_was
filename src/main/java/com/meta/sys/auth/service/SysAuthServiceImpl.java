package com.meta.sys.auth.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.cmm.dto.SearchDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.sys.auth.controller.port.SysAuthService;
import com.meta.sys.auth.controller.request.AuthRequest.AuthCreate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthSetting;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdateSetting;
import com.meta.sys.auth.domain.Auth;
import com.meta.sys.auth.service.port.SysAuthMenuRepository;
import com.meta.sys.auth.service.port.SysAuthRepository;
import com.meta.sys.auth.service.port.SysAuthUserRepository;
import com.meta.usr.user.controller.port.UsrUserService;
import com.meta.usr.user.domain.User;
import com.meta.usr.user.service.port.UsrUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
    
    private final SysAuthRepository sysAuthRepository;
	private final SysAuthMenuRepository sysAuthMenuRepository;
	private final SysAuthUserRepository sysAuthUserRepository;
	private final UsrUserService usrUserService;
	private final UsrUserRepository usrUserRepository;
    
    public Auth getById(Long authId) {
		return sysAuthRepository.findById(authId)
		 	.orElseThrow(() -> new BusinessException("SYS-025")); // 해당 권한은 존재하지 않습니다.
	}
	
	public Page<Auth> selectAuthList(SearchDto searchDto, Pageable pageable) {
    	return sysAuthRepository.selectAuthList(searchDto, pageable);
	}
   

	public Auth insertAuth(@Valid AuthCreate authCreateDto) {
		
		// 권한코드 중복체크
		sysAuthRepository.findByAuthCd(authCreateDto.getAuthCd())
			.ifPresent(a -> { throw new BusinessException("SYS-011"); }); // 중복된 권한 코드 입니다.
		
		Auth auth = authCreateDto.toEntity();
				
		return sysAuthRepository.save(auth);
	}



	public Auth updateAuth(@Valid AuthUpdate authUpdateDto)  {
		
		Auth auth = getById(authUpdateDto.getAuthId());

		// 기본권한(ADMIN, PM, USER, QA)는 변경불가
		if (1 <= auth.getAuthId() && auth.getAuthId() <= 4) {
			throw new BusinessException("SYS-028"); // 해당 권한은 수정할 수 없습니다.
		}

		// 권한 삭제 시, 사용자 권한 제거
		if ("N".equals(authUpdateDto.getUseYn())) {
			auth.getUserList().stream().forEach(authUser -> {
				authUser.getUser().removeRole(auth.getAuthCd());
			});
		}

		auth.changeAuth(authUpdateDto);
				
		return auth;
	}

	public Auth updateAuthSetting(@Valid AuthUpdateSetting authUpdateSettingDto)  {
		
		Auth auth = getById(authUpdateSettingDto.getAuthId());

		sysAuthMenuRepository.deleteByAuth(auth);
		sysAuthUserRepository.deleteByAuth(auth);
		auth.changeAuthSetting(authUpdateSettingDto);

		// 사용자 role테이블에서 해당 auth 전체 지우고
		usrUserRepository.findAll().stream().forEach(user -> {
			user.removeRole(auth.getAuthCd());
		});

		// 사용자 role테이블에 다시 넣기
		authUpdateSettingDto.getUserList().stream().forEach(authUser -> {
			User user = usrUserService.getUser(authUser.getUsrNo());
			user.addRole(auth.getAuthCd());
		});
		 
		return auth;
	}

	public Auth getAuth(@Valid AuthSetting authSettingDto)  {
		return getById(authSettingDto.getAuthId());
	}
}
