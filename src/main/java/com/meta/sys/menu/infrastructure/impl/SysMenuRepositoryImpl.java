package com.meta.sys.menu.infrastructure.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.response.SysMenuResponse.MenuMainResDto;
import com.meta.sys.menu.domain.Menu;
import com.meta.sys.menu.infrastructure.SysMenuJpaRepository;
import com.meta.sys.menu.infrastructure.SysMenuRepositoryCustom;
import com.meta.sys.menu.infrastructure.entity.MenuEntity;
import com.meta.sys.menu.service.port.SysMenuRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SysMenuRepositoryImpl implements SysMenuRepository {

    private final SysMenuJpaRepository sysMenuJpaRepository;
    private final SysMenuRepositoryCustom sysMenuRepositoryCustom;

    @Override
    public List<Menu> findByUseYnAndParentIsNullOrderByMenuSortNo(String string) {
        return sysMenuJpaRepository.findByUseYnAndParentIsNullOrderByMenuSortNo(string)
            .stream().map(MenuEntity::to).toList();
    }

    @Override
    public Page<Menu> selectMenuList(SearchDto searchDto, Pageable pageable) {
        return sysMenuRepositoryCustom.selectMenuList(searchDto, pageable)
            .map(MenuEntity::to);
    }

    @Override
    public List<Menu> searchMenuList(MenuSelect dto) {
        return sysMenuRepositoryCustom.searchMenuList(dto)
            .stream().map(MenuEntity::to).toList();
    }

    @Override
    public List<MenuMainResDto> selectMenuListByUser(long usrNo) {
        return sysMenuRepositoryCustom.selectMenuListByUser(usrNo);
    }

    /* JpaRepository */
    @Override
    public Optional<Menu> findById(Long menuId) {
        return sysMenuJpaRepository.findById(menuId).map(MenuEntity::to);
    }

    @Override
    public Menu save(Menu menu) {
        return sysMenuJpaRepository.save(MenuEntity.from(menu)).to();
    }
}
