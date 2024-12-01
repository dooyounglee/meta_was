package com.meta.sys.auth.infrastructure.impl;

import com.meta.sys.auth.domain.Auth;
import com.meta.sys.auth.infrastructure.SysAuthMenuJpaRepository;
import com.meta.sys.auth.infrastructure.entity.AuthEntity;
import com.meta.sys.auth.service.port.SysAuthMenuRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SysAuthMenuRepositoryImpl implements SysAuthMenuRepository {

    private final SysAuthMenuJpaRepository sysAuthMenuJpaRepository;

    @Override
    public void deleteByAuth(Auth auth) {
        sysAuthMenuJpaRepository.deleteByAuth(AuthEntity.from(auth));
    }
}
