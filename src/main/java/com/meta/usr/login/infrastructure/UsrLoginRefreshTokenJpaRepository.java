package com.meta.usr.login.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.usr.login.infrastructure.entity.RefreshTokenEntity;

@Repository
public interface UsrLoginRefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {
    
    Optional<RefreshTokenEntity> findByUsrNo(Long usrNo);
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
    void deleteByUsrNo(Long usrNo);
}
