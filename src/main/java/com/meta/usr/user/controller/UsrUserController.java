package com.meta.usr.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.cmm.dto.ResponseDto;
import com.meta.cmm.dto.SearchDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.usr.user.controller.port.UsrUserService;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserChangePw;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserCreate;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserPhoto;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserReset;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserUpdate;
import com.meta.usr.user.controller.response.UsrUserResponse;
import com.meta.usr.user.domain.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usr/user")
@RequiredArgsConstructor
public class UsrUserController {
    
    private final UsrUserService usrUserService;

    @PostMapping(value = "/create")
    public ResponseDto create(@RequestBody UsrUserCreate createDto) throws BusinessException {
        usrUserService.create(createDto);
        return new ResponseDto();
    }

    @PutMapping(value = "/create")
    public ResponseDto update(@RequestBody UsrUserUpdate updateDto) throws BusinessException {
        usrUserService.update(updateDto);
        return new ResponseDto();
    }
    
    @PutMapping(value = "/reset")
    public ResponseDto reset(@RequestBody UsrUserReset requestDto) throws BusinessException {
        usrUserService.reset(requestDto);
        return new ResponseDto();
    }
    
    @GetMapping(value = "/selectAllUser")
    public ResponseEntity<ResponseDto> searchUsrNm(SearchDto searchDto) throws BusinessException {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> data = new HashMap<>();
    	
        data.put("userList", usrUserService.selectAllUser(searchDto));

        responseDto.setData(data);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/info")
    public ResponseDto info() throws BusinessException {
        User user = usrUserService.getUser();
        
        ResponseDto responseDto = ResponseDto.builder()
            .data(UsrUserResponse.from(user))
            .build();
        return responseDto;
    }

    @PostMapping(value = "/changePassword")
    public ResponseDto changePassword(@RequestBody UsrUserChangePw changePwDto) throws BusinessException {
        usrUserService.changePassword(changePwDto);
        return new ResponseDto();
    }

    @PutMapping(value = "/photo")
    public ResponseDto photo(@RequestBody UsrUserPhoto photoDto) throws BusinessException {
        usrUserService.photo(photoDto);
        return new ResponseDto();
    }
}
