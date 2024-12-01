package com.meta.cmm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.meta.cmm.exception.BusinessException;
import com.meta.usr.login.domain.RefreshToken;
import com.meta.usr.login.service.port.UsrLoginRefreshTokenRepository;
import com.meta.usr.user.controller.port.UsrUserService;
import com.meta.usr.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UsrUserService usrUserService;
    private final UsrLoginRefreshTokenRepository usrLoginRefreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
        HttpServletResponse servletResponse,
        FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String newToken = "";

        String validation = jwtTokenProvider.validateToken(token);
        if (token != null && "exception".equals(validation)) {
            throw new BusinessException("SYS-017"); // 잘못된 토큰값 입니다.
        } else if ("valid".equals(validation)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if ("expired".equals(validation)) {
        	RefreshToken refreshToken = usrLoginRefreshTokenRepository.findByRefreshToken(jwtTokenProvider.resolveRefreshToken(servletRequest))
                    .orElseThrow(() -> new BusinessException("SYS-017")); // 잘못된 토큰값 입니다.
            log.debug("refreshToken: {}", refreshToken);
            
            User user = usrUserService.getUserWithRole(refreshToken.getUsrNo());
            
            newToken = jwtTokenProvider.createToken(String.valueOf(user.getUsrNo()), user.getRoles());
            Authentication authentication = jwtTokenProvider.getAuthentication(newToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 만료일때는 새로운 token값을 header로 보내주기
            servletResponse.setHeader("X-AUTH-TOKEN", newToken);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
