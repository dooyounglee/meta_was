package com.meta.usr.user.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.usr.user.infrastructure.entity.UserEntity;
import com.querydsl.core.BooleanBuilder;

@Repository
public interface UsrUserJpaRepository extends JpaRepository<UserEntity, Long>, UsrUserRepositoryCustom {

	Optional<UserEntity> findByUsrId(String usrId);
	List<UserEntity> findAllByOrderByUsrNo();
    Page<UserEntity> findAll(BooleanBuilder booleanBuilder, Pageable pageable);
}
