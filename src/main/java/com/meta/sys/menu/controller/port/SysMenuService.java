package com.meta.sys.menu.controller.port;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuCreate;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuUpdate;
import com.meta.sys.menu.domain.Menu;

public interface SysMenuService {

    Page<Menu> selectMenuList(SearchDto searchDto, Pageable pageable);
    List<Menu> selectMenuListAll();
    Menu insertMenu(MenuCreate menuCreateDto);
    Menu updateMenu(MenuUpdate menuUpdateDto);
    List<Menu> searchMenuList(MenuSelect menuSelect);
    
}
