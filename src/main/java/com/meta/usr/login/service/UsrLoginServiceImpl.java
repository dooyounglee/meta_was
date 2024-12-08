package com.meta.usr.login.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.cmm.exception.BusinessException;
import com.meta.cmm.security.JwtTokenProvider;
import com.meta.sys.menu.service.port.SysMenuRepository;
import com.meta.sys.message.controller.port.SysMessageService;
import com.meta.sys.view.controller.port.SysViewService;
import com.meta.usr.login.controller.port.UsrLoginService;
import com.meta.usr.login.controller.request.UsrLoginRequest.UsrLoginLogin;
import com.meta.usr.login.domain.RefreshToken;
// import com.meta.usr.login.dto.TokenDto;
import com.meta.usr.login.service.port.UsrLoginRefreshTokenRepository;
import com.meta.usr.user.controller.response.UsrUserResponse;
import com.meta.usr.user.domain.User;
import com.meta.usr.user.service.port.UsrUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsrLoginServiceImpl implements UsrLoginService {
    
    public final UsrUserRepository usrUserRepository;
    public final UsrLoginRefreshTokenRepository usrLoginRefreshTokenRepository;
    public final JwtTokenProvider jwtTokenProvider;
    public final PasswordEncoder passwordEncoder;
    public final SysMenuRepository sysMenuRepository;
    private final SysViewService sysViewService;
    private final SysMessageService sysMessageService;
    
    public Map<String, Object> signIn(UsrLoginLogin loginDto) {
        log.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        
        User user = usrUserRepository.findByUsrId(loginDto.getUsrId())
            .orElseThrow(() -> new BusinessException("SYS-015")); // 아이디 또는 패스워드를 확인해주세요.
        System.out.println(user.getPassword());
        System.out.println(loginDto.getUsrPw());
        
        // 패스워드 비교 수행
        if (loginDto.getUsrPw() == null || loginDto.getUsrPw().trim().length() == 0 || !passwordEncoder.matches(loginDto.getUsrPw(), user.getPassword())) {
            throw new BusinessException("SYS-015"); // 아이디 또는 패스워드를 확인해주세요.
        }

        // 권한없을 시, 로그인 불가
        log.debug("roles", user.getRoles());
        if (user.getRoles().size() == 0) {
            throw new BusinessException("SYS-027"); // 권한이 없습니다. 관리자에게 문의해주세요.
        }

        // responseMap에 token, user 담기
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", jwtTokenProvider.createToken(String.valueOf(user.getUsrNo()), user.getRoles()));
        responseMap.put("refreshToken", jwtTokenProvider.createRefreshToken(String.valueOf(user.getUsrNo())));
        responseMap.put("user", UsrUserResponse.builder()
                                        .usrNo(user.getUsrNo())
                                        .usrId(user.getUsrId())
                                        .usrNm(user.getUsrNm())
                                        .roles(user.getRoles())
                                        .build());

        // refreshToken DB에 넣기
        usrLoginRefreshTokenRepository.save(
            RefreshToken.builder()
            .usrNo(user.getUsrNo())
            .refreshToken((String) responseMap.get("refreshToken"))
            .build());
        
        responseMap.put("messages", sysMessageService.all()); // 메시지
        responseMap.put("menus", sysMenuRepository.selectMenuListByUser(user.getUsrNo())); // 메뉴
        responseMap.put("views", sysViewService.selectViewAll()); // 화면

        return responseMap;
    }

    // public TokenDto refreshToken(TokenDto token) {

    //     RefreshToken refreshToken = usrLoginRefreshTokenRepository.findByRefreshToken(token.getRefreshToken())
	// 			.orElseThrow(() -> new BusinessException("SYS-017")); // 잘못된 토큰값 입니다.
	// 	log.debug("refreshToken1: {}", refreshToken);
		
	// 	User user = usrUserRepository.findById(refreshToken.getUsrNo())
	// 			.orElseThrow(() -> new BusinessException("SYS-018")); // 사용자가 정보가 없습니다.
	// 	log.debug("user: {}", user);
		
	// 	token.setNewToken(jwtTokenProvider.createRefreshToken(String.valueOf(user.getUsrId())));
	// 	log.debug("newToken: {}", token.getNewToken());
		
	// 	return token;
    // }

    @PostConstruct
    private void createAdmin() {
        
        User admin = User.builder()
            .usrId("admin")
            .usrPw("admin")
            .useYn("Y")
            .usrNm("관리자")
            .roles(Collections.singletonList("ADMIN"))
            .build();
        
        admin.changePw(passwordEncoder.encode(admin.getPassword()));
        
        // 이미 있는지 확인
        usrUserRepository.findByUsrId(admin.getUsrId())
            .orElseGet(() -> usrUserRepository.save(admin));
    }

















    public Map<String, Object> signIntest(UsrLoginLogin dto) {
        
        // 없으면 만들기
        User saved = null;
        Optional<User> oUser = usrUserRepository.findByUsrId(dto.getUsrId());
        if (!oUser.isPresent()) {
            saved = usrUserRepository.save(dto.to(passwordEncoder.encode(dto.getUsrPw())));
        } else {
            saved = oUser.get();
        }

        // 로그인
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", jwtTokenProvider.createToken(String.valueOf(saved.getUsrNo()), saved.getRoles()));
        responseMap.put("refreshToken", jwtTokenProvider.createRefreshToken(String.valueOf(saved.getUsrNo())));
        responseMap.put("user", UsrUserResponse.builder()
                                        .usrNo(saved.getUsrNo())
                                        .usrId(saved.getUsrId())
                                        .usrNm(saved.getUsrNm())
                                        .roles(saved.getRoles())
                                        .build());

        // refreshToken DB에 넣기
        usrLoginRefreshTokenRepository.save(
            RefreshToken.builder()
            .usrNo(saved.getUsrNo())
            .refreshToken((String) responseMap.get("refreshToken"))
            .build());
        
        // 메뉴
        responseMap.put("menus", sysMenuRepository.selectMenuListByUser(saved.getUsrNo()));
        responseMap.put("views", sysViewService.selectViewAll()); // 화면

        return responseMap;
    }
}
