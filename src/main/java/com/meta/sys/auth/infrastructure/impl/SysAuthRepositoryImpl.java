package com.meta.sys.auth.infrastructure.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.domain.Auth;
import com.meta.sys.auth.infrastructure.SysAuthJpaRepository;
import com.meta.sys.auth.infrastructure.SysAuthRepositoryCustom;
import com.meta.sys.auth.infrastructure.entity.AuthEntity;
import com.meta.sys.auth.service.port.SysAuthRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SysAuthRepositoryImpl implements SysAuthRepository {

	private final SysAuthJpaRepository sysAuthJpaRepository;
	private final SysAuthRepositoryCustom sysAuthRepositoryCustom;

	@Override
	public Optional<Auth> findByAuthCd(String authCd) {
		return sysAuthJpaRepository.findByAuthCd(authCd)
			.map(AuthEntity::to);
	}

	@Override
	public Page<Auth> selectAuthList(SearchDto searchDto, Pageable pageable) {
		return sysAuthRepositoryCustom.selectAuthList(searchDto, pageable)
			.map(AuthEntity::to);
	}

	@Override
	public Optional<Auth> findById(Long authId) {
		return sysAuthJpaRepository.findById(authId)
			.map(AuthEntity::to);
	}

	@Override
	public Auth save(Auth auth) {
		return sysAuthJpaRepository.save(AuthEntity.from(auth)).to();
	}

}
