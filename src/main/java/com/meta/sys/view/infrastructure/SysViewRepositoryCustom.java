package com.meta.sys.view.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.domain.View;

public interface SysViewRepositoryCustom {
	
	Page<View> selectViewList(SearchDto searchDto, Pageable pageable);
	List<View> searchViewList(SearchDto searchDto);
}
