package com.meta.usr.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.cmm.dto.SearchDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.cmm.util.SecurityUtil;
import com.meta.usr.user.controller.port.UsrUserService;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserChangePw;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserCreate;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserPhoto;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserReset;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserUpdate;
import com.meta.usr.user.domain.User;
import com.meta.usr.user.service.port.UsrUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsrUserServiceImpl implements UsrUserService {
    
    public final UsrUserRepository usrUserRepository;
    public final PasswordEncoder passwordEncoder;

    public User create(UsrUserCreate createDto) throws BusinessException {
        
        // 중복체크
        usrUserRepository.findByUsrId(createDto.getUsrId())
            .ifPresent(m -> { throw new BusinessException("SYS-014"); }); // 이미 등록된 ID 입니다.

        // 패스워드 초기화&암호화
        User user = createDto.toEntity(passwordEncoder.encode(initialPassword(createDto)));
        return usrUserRepository.save(user);
    }

    public User update(UsrUserUpdate updateDto) throws BusinessException {
        User user = getUser(updateDto.getUsrNo());
        user.update(updateDto);
        return usrUserRepository.save(user);
    }

    public User reset(UsrUserReset requestDto) {
        User user = getUser(requestDto.getUsrNo());
        user.changePw(passwordEncoder.encode(initialPassword(user)));
        return usrUserRepository.save(user);
    }

    private String initialPassword(UsrUserCreate createDto) {
        return createDto.getUsrId() + createDto.getUsrPhone().substring(createDto.getUsrPhone().length() - 4);
    }

    private String initialPassword(User user) {
        return user.getUsrId() + user.getUsrPhone().substring(user.getUsrPhone().length() - 4);
    }

    public long getUsrNo() {
        return SecurityUtil.getUser().getUsrNo();
    }

    public User getUser() {
        return getUser(getUsrNo());
    }

    public User getUser(long usrNo) {
        return usrUserRepository.findById(usrNo)
        .orElseThrow(() -> new BusinessException("SYS-018")); // 사용자가 정보가 없습니다.
    }

    public User getUser(String usrId) {
        return usrUserRepository.findByUsrId(usrId)
            .orElseThrow(() -> new BusinessException("SYS-018", new String[] {usrId})); // 사용자가 정보가 없습니다.
    }

    public boolean hasAuth(String... auth) {
        for (String a : auth) {
            if (SecurityUtil.getUser().getRoles().indexOf("ROLE_" + a) > -1) return true;
        }
        return false;
    }

    public User getUserWithRole(long usrNo) {
        User user = getUser(usrNo);
        user.getRoles().stream().forEach(role -> {});
        return user;
    }
    
    public List<User> all() {
        return selectAllUser(SearchDto.builder()./*pjtId(0L).*/searchWrd("").build());
    }

    public List<User> selectAllUser(SearchDto dto) {
        return usrUserRepository.selectAllUser(dto);
	}

    public User changePassword(UsrUserChangePw changePwDto) {
        User user = getUser();
        
        if (!passwordEncoder.matches(changePwDto.getUsrPw(), user.getPassword())) {
            throw new BusinessException("SYS-019"); // 비번이 맞지 않습니다. 다시 확인 해주세요.
        }

        user.changePw(passwordEncoder.encode(changePwDto.getNewPw()));
        return user;
    }

    public User photo(UsrUserPhoto photoDto) {
        User user = getUser();
        user.photo(photoDto);
        return usrUserRepository.save(user);
    }
}
