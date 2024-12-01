package com.meta.usr.user.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.usr.user.domain.User;
import com.querydsl.core.BooleanBuilder;

public interface UsrUserRepository {

    /* Query Method */
    Optional<User> findByUsrId(String usrId);
    
    /* Query Dsl */
    List<User> selectAllUser(SearchDto dto);
    
    /* JpaRepository */
    List<User> findAll();
    Page<User> findAll(BooleanBuilder booleanBuilder, Pageable pageable);
    Optional<User> findById(long usrNo);
    User save(User user);
}
