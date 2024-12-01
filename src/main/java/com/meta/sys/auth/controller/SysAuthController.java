package com.meta.sys.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.cmm.dto.ResponseDto;
import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.controller.port.SysAuthService;
import com.meta.sys.auth.controller.request.AuthRequest.AuthCreate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthSetting;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdateSetting;
import com.meta.sys.auth.controller.response.AuthResponse;
import com.meta.sys.auth.domain.Auth;
import com.meta.sys.menu.controller.port.SysMenuService;
import com.meta.usr.user.controller.port.UsrUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys/auth")
@RequiredArgsConstructor
public class SysAuthController {
    
    private final SysAuthService sysAuthService;
	private final UsrUserService usrUserService;
	private final SysMenuService sysMenuService;

    @GetMapping(value = "/selectAuthList")
    public ResponseEntity<ResponseDto> selectAuthList(SearchDto searchDto ,@PageableDefault(size = 10 ,page = 0) Pageable pageable) {
    	
    	Page<AuthResponse> data = sysAuthService.selectAuthList(searchDto, pageable).map(AuthResponse::to);
    	
    	ResponseDto responseDto = ResponseDto.builder()
    	    	.data(data)
    	    	.build();
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @PostMapping(value = "/insertAuth")
    public ResponseEntity<ResponseDto> insertAuth(@Valid @RequestBody AuthCreate authCreateDto) {
    	
    	Auth auth = sysAuthService.insertAuth(authCreateDto);
    	AuthResponse result = AuthResponse.to(auth);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(result)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}
    
    @PutMapping(value = "/updateAuth")
    public ResponseEntity<ResponseDto> updateAuth(@Valid @RequestBody AuthUpdate authUpdateDto) {
    	
    	Auth auth = sysAuthService.updateAuth(authUpdateDto);
    	AuthResponse result = AuthResponse.to(auth);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(result)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping(value = "/setting")
    public ResponseEntity<ResponseDto> setting(AuthSetting authSettingDto) {
    	
		Map<String, Object> data = new HashMap<>();

		data.put("auth", AuthResponse.toDetail(sysAuthService.getAuth(authSettingDto)));
		data.put("userList", usrUserService.all());
		data.put("menuList", sysMenuService.selectMenuListAll());
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(data)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping(value = "/updateAuthSetting")
    public ResponseEntity<ResponseDto> updateAuthSetting(@Valid @RequestBody AuthUpdateSetting authUpdateSettingDto) {
    	
    	Auth auth = sysAuthService.updateAuthSetting(authUpdateSettingDto);
		AuthResponse result = AuthResponse.to(auth);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(result)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}

}
