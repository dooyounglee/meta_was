package com.meta.sys.auth.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meta.sys.auth.infrastructure.entity.AuthEntity;

@Repository
public interface SysAuthJpaRepository extends JpaRepository<AuthEntity, Long> {

	@Query("select distinct cd from Auth cd")
	// List<AuthEntity> selectAllAuthFetch();
    Optional<AuthEntity> findByAuthCd(String authCd);
}
