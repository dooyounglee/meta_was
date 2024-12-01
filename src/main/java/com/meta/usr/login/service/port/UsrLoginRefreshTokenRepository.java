package com.meta.usr.login.service.port;

import java.util.Optional;

import com.meta.usr.login.domain.RefreshToken;

public interface UsrLoginRefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
