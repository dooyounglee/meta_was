package com.meta.sys.auth.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.sys.auth.infrastructure.entity.AuthEntity;
import com.meta.sys.auth.infrastructure.entity.AuthUserEntity;

public interface SysAuthUserJpaRepository extends JpaRepository<AuthUserEntity, Long> {
    
    void deleteByAuth(AuthEntity auth);
}
