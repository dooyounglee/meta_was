package com.meta.cmm.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.meta.usr.user.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${Globals.jwt.secret}")
    private String secretKey;
    private final long tokenValidMillisecond = 1000L * 60 * 60 * 1; // 1시간 토큰 유효
    private final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24; // 24시간 토큰 유효

    /**
     * SecretKey 에 대해 인코딩 수행
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 생성
    public String createToken(String usrNo, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(usrNo);
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
            .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
            .compact();
        
        return token;
    }

    public String createRefreshToken(String usrNo) {
        Claims claims = Jwts.claims().setSubject(usrNo);

        Date now = new Date();
        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
            .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
            .compact();
        
        return token;
    }

    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails user = (UserDetails) User.builder()
                                            .usrNo(Long.valueOf(getUsrNo(token)))
                                            .roles(getUsrRoles(token))
                                            .build();
        return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    // JWT 토큰에서 회원 구별 정보 추출
    public String getUsrNo(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getUsrRoles(String token) {
        // return ((List<String>) getClaims(token).get("roles")).stream().map(SimpleGrantedAuthority::new).to;
        // return Set.copyOf(((List<String>) getClaims(token).get("roles")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        return (List<String>) getClaims(token).get("roles");
    }

    /**
     * HTTP Request Header 에 설정된 토큰 값을 가져옴
     *
     * @param request Http Request Header
     * @return String type Token 값
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-REFRESH-TOKEN");
    }

    // JWT 토큰의 유효성 + 만료일 체크
    public String validateToken(String token) {
        if (token == null) return null;

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return "valid";
        } catch (ExpiredJwtException e) {
            log.debug("[validateToken] 토큰 유효기간 만료");
            return "expired";
        } catch (Exception e) {
            log.info("[validateToken] 토큰 유효 체크 예외 발생");
            return "exception";
        }
    }
}
