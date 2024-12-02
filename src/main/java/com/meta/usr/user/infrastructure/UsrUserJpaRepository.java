package com.meta.usr.user.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.meta.usr.user.infrastructure.entity.UserEntity;

@Repository
public interface UsrUserJpaRepository extends JpaRepository<UserEntity, Long>, QuerydslPredicateExecutor<UserEntity>, UsrUserRepositoryCustom {

	Optional<UserEntity> findByUsrId(String usrId);
	List<UserEntity> findAllByOrderByUsrNo();
}
