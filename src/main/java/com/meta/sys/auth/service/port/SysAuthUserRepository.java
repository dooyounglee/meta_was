package com.meta.sys.auth.service.port;

import com.meta.sys.auth.domain.Auth;

public interface SysAuthUserRepository {

    void deleteByAuth(Auth auth);
}
