package com.meta.usr.user.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserPhoto;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserUpdate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User implements UserDetails {
    
    private long usrNo;
    private String usrId;
    private String usrNm;
    private String usrPw;
    private String useYn;
    private String usrComp;
    private String usrPos;
    private String usrPhone;
    private String usrPhoto;
    private List<String> roles;

    @Override
    public String getPassword() {
        return this.usrPw;
    }

    @Override
	public String getUsername() {
		return this.usrNm;
	}

    @Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public boolean addRole(String role) {
        boolean result = this.roles.add("ROLE_" + role);
        this.roles = this.roles.stream().distinct().collect(Collectors.toList());
        return result;
    }

    public boolean removeRole(String role) {
        return this.roles.remove("ROLE_" + role);
    }

    public void update(UsrUserUpdate updateDto) {
        usrNm = updateDto.getUsrNm();
        useYn = updateDto.getUseYn();
        usrComp = updateDto.getUsrComp();
        usrPos = updateDto.getUsrPos();
        usrPhone = updateDto.getUsrPhone();
    }

    public void changePw(String encodedPw) {
        usrPw = encodedPw;
    }

    public void photo(UsrUserPhoto photoDto) {
        usrPhoto = photoDto.getUsrPhoto();
    }
}
