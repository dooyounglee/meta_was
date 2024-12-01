package com.meta.sys.auth.infrastructure.impl;

import com.meta.sys.auth.domain.Auth;
import com.meta.sys.auth.infrastructure.SysAuthUserJpaRepository;
import com.meta.sys.auth.infrastructure.entity.AuthEntity;
import com.meta.sys.auth.service.port.SysAuthUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SysAuthUserRepositoryImpl implements SysAuthUserRepository {

    private final SysAuthUserJpaRepository sysAuthUserJpaRepository;

    @Override
    public void deleteByAuth(Auth auth) {
        sysAuthUserJpaRepository.deleteByAuth(AuthEntity.from(auth));
    }
}
