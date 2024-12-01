package com.meta.sys.menu.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.response.SysMenuResponse.MenuMainResDto;
import com.meta.sys.menu.domain.Menu;

public interface SysMenuRepository {

    /* Query Method */
    List<Menu> findByUseYnAndParentIsNullOrderByMenuSortNo(String string);

    /* Query Dsl */
    Page<Menu> selectMenuList(SearchDto searchDto, Pageable pageable);
    List<Menu> searchMenuList(MenuSelect dto);
    List<MenuMainResDto> selectMenuListByUser(long usrNo);

    /* JpaRepository */
    Optional<Menu> findById(Long menuId);
    Menu getOne(Long parentMenuId);
    Menu save(Menu menu);
}
