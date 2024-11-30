package com.meta.sys.view.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.infrastructure.entity.ViewEntity;

public interface SysViewRepositoryCustom {
	
	Page<ViewEntity> selectViewList(SearchDto searchDto, Pageable pageable);
	List<ViewEntity> searchViewList(SearchDto searchDto);
}
