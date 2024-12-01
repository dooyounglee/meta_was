package com.meta.sys.auth.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.infrastructure.entity.AuthEntity;

public interface SysAuthRepositoryCustom {
	
	Page<AuthEntity> selectAuthList(SearchDto searchDto, Pageable pageable);
}
