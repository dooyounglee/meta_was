package com.meta.cmm.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.meta.usr.login.service.port.UsrLoginRefreshTokenRepository;
import com.meta.usr.user.controller.port.UsrUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UsrUserService usrUserService;
    private final UsrLoginRefreshTokenRepository usrLoginRefreshTokenRepository;
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity// .httpBasic().disable() // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
        .httpBasic(httpBasic -> httpBasic.disable())
        .csrf(csrf -> csrf.disable()) // .csrf().disable()
        .cors(cors -> cors.disable()) // .cors().and()
        
        .sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // .sessionManagement()
        // .sessionCreationPolicy(
        //     SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
        // .and()
        .authorizeRequests(authorizeRequests -> 
            authorizeRequests // 리퀘스트에 대한 사용권한 체크
            .antMatchers("/usr/login/signup", "/usr/login/signin").permitAll() // 가입 및 로그인 주소는 허용
            .antMatchers("/socket").permitAll() // socket은 모두 허용 (JWT filter를 안거치나 봄)
            .antMatchers("/cmm/file/download").permitAll() // 첨부파일 다운로드는 get호출
            .antMatchers("/usr/login/signin/test").permitAll() // 테스트 로그인 주소는 허용
            .antMatchers("/cmm/file/images/**").permitAll() // 
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // cors preflight 모두 허용
            
            // .antMatchers("/**").permitAll() // 로그인없이 개발할때 주석풀기
			.antMatchers("/sys/project/indvProject/List").permitAll() // 전체 화면목록 조회 허용
            .antMatchers("/sys/board/useList").permitAll() // 전체 게시판목록 조회 허용

            // .antMatchers(HttpMethod.GET, "/product/**").permitAll() // product로 시작하는 Get 요청은 허용
            // .antMatchers("**exception**").permitAll()
            .antMatchers("/sys/**").access("hasAuthority('ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PM')")
            .anyRequest().access("hasAuthority('ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PM') or hasRole('ROLE_PL') or hasRole('ROLE_USER') or hasRole('ROLE_QA')"))
            // .anyRequest().hasAnyRole("ADMIN","PL","PM","USER"))
        .exceptionHandling((exceptionHandling) -> 
            exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())) // .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, usrUserService, usrLoginRefreshTokenRepository),
            UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가
	}
	
	@Override
    public void configure(WebSecurity webSecurity) {
		webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/attach/images/**",
	            "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger-ui/**", "/sign-api/exception");
	}
}
