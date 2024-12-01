package com.meta.sys.menu.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.cmm.dto.SearchDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuCreate;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuUpdate;
import com.meta.sys.menu.domain.Menu;
import com.meta.sys.menu.service.port.SysMenuRepository;
import com.meta.sys.view.domain.View;
import com.meta.sys.view.service.port.SysViewRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl {
    
    private final SysMenuRepository sysMenuRepository;
    private final SysViewRepository sysViewRepository;
    
    public Page<Menu> selectMenuList(SearchDto searchDto, Pageable pageable) {
    	return sysMenuRepository.selectMenuList(searchDto, pageable);
	}
    
    public List<Menu> selectMenuListAll() {
        List<Menu> list = sysMenuRepository.findByUseYnAndParentIsNullOrderByMenuSortNo("Y");
        return list;
    }

	public Menu insertMenu(@Valid MenuCreate menuCreate) {
		
		Menu menu = menuCreate.to();
		View view = sysViewRepository.getOne(menuCreate.getViewNo());
		Menu parentMenu = null;
		
		if(menuCreate.getParentMenuId() != null && menuCreate.getMenuLevel() > 1 ) {
			parentMenu = sysMenuRepository.getOne(menuCreate.getParentMenuId());
		}
		
		menu.setView(view);
		menu.setParentMenu(parentMenu);
				
		return sysMenuRepository.save(menu);
	}


	public Menu updateMenu(@Valid MenuUpdate menuUpdate)  {

		Menu menu = sysMenuRepository.findById(menuUpdate.getMenuId())
			.orElseThrow(() -> new BusinessException("SYS-012")); // 해당 메뉴가 존재하지 않습니다.
		 
		View view = sysViewRepository.getOne(menuUpdate.getViewNo());
		Menu parentMenu = null;
		 
		if(menuUpdate.getParentMenuId() != null && menuUpdate.getMenuLevel() > 1 ) {
			parentMenu = sysMenuRepository.getOne(menuUpdate.getParentMenuId());
		}
		
		menu.setView(view);
		menu.setParentMenu(parentMenu);
		menu.changeMenu(menuUpdate);
		 
		return menu;
	}


    
    public List<Menu> searchMenuList(MenuSelect dto) {
    	return sysMenuRepository.searchMenuList(dto);
	}

}
