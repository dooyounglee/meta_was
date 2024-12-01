package com.meta.sys.menu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.meta.sys.menu.controller.request.SysMenuRequest.MenuUpdate;
import com.meta.sys.view.domain.View;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class Menu {
    
    private Long menuId;
    private String menuNm;
    private int menuLevel;
    private Menu parent;
    private int menuSortNo;
    private String useYn;
    @Builder.Default
    private List<Menu> children= new ArrayList<>();
    private View view;

    private LocalDateTime createdDate;

    public void changeMenu(MenuUpdate dto) {
        this.menuNm = dto.getMenuNm();
        this.menuLevel = dto.getMenuLevel();
        this.menuSortNo = dto.getMenuSortNo();
    	this.useYn = dto.getUseYn();
    }
    
    public void setParentMenu(Menu parent) {
    	this.parent = parent;
    }
    
    public void setView(View view) {
    	this.view = view;
    }
}
