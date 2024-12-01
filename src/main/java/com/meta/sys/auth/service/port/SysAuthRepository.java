package com.meta.sys.auth.service.port;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.domain.Auth;

public interface SysAuthRepository {

    /* Query Method */
    Optional<Auth> findByAuthCd(String authCd);

    /* Query Dsl */
    Page<Auth> selectAuthList(SearchDto searchDto, Pageable pageable);
    
    /* JpaRepository */
    Optional<Auth> findById(Long authId);
    Auth save(Auth auth);
}
