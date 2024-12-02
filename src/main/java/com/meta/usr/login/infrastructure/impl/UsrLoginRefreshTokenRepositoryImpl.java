package com.meta.usr.login.infrastructure.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meta.usr.login.domain.RefreshToken;
import com.meta.usr.login.infrastructure.UsrLoginRefreshTokenJpaRepository;
import com.meta.usr.login.infrastructure.entity.RefreshTokenEntity;
import com.meta.usr.login.service.port.UsrLoginRefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsrLoginRefreshTokenRepositoryImpl implements UsrLoginRefreshTokenRepository {
    
    private final UsrLoginRefreshTokenJpaRepository usrLoginRefreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return usrLoginRefreshTokenJpaRepository.save(RefreshTokenEntity.from(refreshToken)).to();
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return usrLoginRefreshTokenJpaRepository.findByRefreshToken(refreshToken).map(RefreshTokenEntity::to);
    }
}
