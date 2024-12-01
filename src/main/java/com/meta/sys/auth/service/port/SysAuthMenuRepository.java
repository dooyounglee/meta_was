package com.meta.sys.auth.service.port;

import com.meta.sys.auth.domain.Auth;

public interface SysAuthMenuRepository {

    void deleteByAuth(Auth auth);
}
