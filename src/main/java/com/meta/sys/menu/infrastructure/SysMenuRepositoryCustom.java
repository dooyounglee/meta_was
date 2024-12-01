package com.meta.sys.menu.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.response.SysMenuResponse.MenuMainResDto;
import com.meta.sys.menu.infrastructure.entity.MenuEntity;

public interface SysMenuRepositoryCustom {
	
	Page<MenuEntity> selectMenuList(SearchDto searchDto, Pageable pageable);
	List<MenuEntity> searchMenuList(MenuSelect menuSelect);
	List<MenuMainResDto> selectMenuListByUser(long userNo);
}
