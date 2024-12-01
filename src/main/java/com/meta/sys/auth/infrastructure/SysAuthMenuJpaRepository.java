package com.meta.sys.auth.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.sys.auth.infrastructure.entity.AuthEntity;
import com.meta.sys.auth.infrastructure.entity.AuthMenuEntity;

public interface SysAuthMenuJpaRepository extends JpaRepository<AuthMenuEntity, Long> {
    
    void deleteByAuth(AuthEntity auth);
}
