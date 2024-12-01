package com.meta.sys.auth.controller.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.controller.request.AuthRequest.AuthCreate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthSetting;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdate;
import com.meta.sys.auth.controller.request.AuthRequest.AuthUpdateSetting;
import com.meta.sys.auth.domain.Auth;

public interface SysAuthService {

    Page<Auth> selectAuthList(SearchDto searchDto, Pageable pageable);
    Auth insertAuth(AuthCreate authCreateDto);
    Auth updateAuth(AuthUpdate authUpdateDto);
    Auth getAuth(AuthSetting authSettingDto);
    Auth updateAuthSetting(AuthUpdateSetting authUpdateSettingDto);
}
