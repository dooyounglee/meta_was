package com.meta.usr.login.infrastructure.impl;

import java.util.Optional;

import com.meta.usr.login.domain.RefreshToken;
import com.meta.usr.login.infrastructure.UsrLoginRefreshTokenJpaRepository;
import com.meta.usr.login.infrastructure.entity.RefreshTokenEntity;
import com.meta.usr.login.service.port.UsrLoginRefreshTokenRepository;

public class UsrLoginRefreshTokenRepositoryImpl implements UsrLoginRefreshTokenRepository {
    
    private UsrLoginRefreshTokenJpaRepository usrLoginRefreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return usrLoginRefreshTokenJpaRepository.save(RefreshTokenEntity.from(refreshToken)).to();
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return usrLoginRefreshTokenJpaRepository.findByRefreshToken(refreshToken).map(RefreshTokenEntity::to);
    }
}
