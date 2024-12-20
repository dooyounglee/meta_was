package com.meta.usr.login.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.cmm.dto.ResponseDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.usr.login.controller.port.UsrLoginService;
import com.meta.usr.login.controller.request.UsrLoginRequest.UsrLoginLogin;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usr/login")
@RequiredArgsConstructor
public class UsrLoginController {
    
    private final UsrLoginService UsrLoginService;
	
	@PostMapping(value = "/signin")
    public ResponseDto signIn(@RequestBody UsrLoginLogin loginDto) throws BusinessException {
        Map<String, Object> responseMap = UsrLoginService.signIn(loginDto);
        
        ResponseDto responseDto = ResponseDto.builder()
        		.data(responseMap)
        		.build();
        return responseDto;
    }



















    @PostMapping(value = "/signin/test")
    public ResponseDto signIntest(@RequestBody UsrLoginLogin loginDto) throws BusinessException {
        Map<String, Object> responseMap = UsrLoginService.signIntest(loginDto);
        
        ResponseDto responseDto = ResponseDto.builder()
        		.data(responseMap)
        		.build();
        return responseDto;
    }
}
