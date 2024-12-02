package com.meta.sys.menu.infrastructure.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.infrastructure.entity.QAuthMenuEntity;
import com.meta.sys.auth.infrastructure.entity.QAuthUserEntity;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.response.SysMenuResponse.MenuMainResDto;
import com.meta.sys.menu.infrastructure.SysMenuRepositoryCustom;
import com.meta.sys.menu.infrastructure.entity.MenuEntity;
import com.meta.sys.menu.infrastructure.entity.QMenuEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SysMenuRepositoryCustomImpl implements SysMenuRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	QMenuEntity menu = QMenuEntity.menuEntity;

	@Override
	public Page<MenuEntity> selectMenuList(SearchDto searchDto, Pageable pageable) {
		
		List<MenuEntity> content = queryFactory
				.select(Projections.bean(MenuEntity.class,
						menu.menuId,
						menu.menuNm,
						menu.menuLevel,
						menu.menuSortNo,
						menu.useYn,
						menu.createdDate,
						menu.parent.menuId,
						menu.parent.menuNm,
						menu.view.viewNo,
						menu.view.viewPath
						))
				.from(menu)
				.leftJoin(menu.parent)
				.leftJoin(menu.view)
				.where(setSearchCondition(searchDto))
				.orderBy(menu.menuId.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		JPAQuery<Long> countQuery = queryFactory
				.select(menu.count())
				.from(menu)
				.where(setSearchCondition(searchDto));
		
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne );
	}
	
	@Override
	public List<MenuEntity> searchMenuList(MenuSelect menuSelect) {
		Integer menuLevel = menuSelect.getMenuLevel();
		
		List<MenuEntity> list =queryFactory
				.selectFrom(menu)
				.where(	menu.useYn.eq("Y"),
						setSearchCondition(menuSelect),
						menuLevel != null ? menu.menuLevel.eq(menuLevel-1): null )
				.fetch();
		
		return list;
	}
	
	private BooleanBuilder setSearchCondition(SearchDto dto) {

		String searchTyp = dto.getSearchTyp();
		String searchWrd = dto.getSearchWrd();
		
		BooleanBuilder booleanBuilder1 = new BooleanBuilder();
		
		if(StringUtils.isBlank(searchWrd)){
			return null;
		}
		
		if (!StringUtils.isBlank(searchWrd)) {
			if (searchTyp.contains("a")) booleanBuilder1.or(menu.menuNm.upper().contains(searchWrd.toUpperCase()));
			if (searchTyp.contains("b")) booleanBuilder1.or(menu.view.viewPath.upper().contains(searchWrd.toUpperCase()));
		}
		
		return booleanBuilder1;
	}

	@Override
	public List<MenuMainResDto> selectMenuListByUser(long usrNo) {
		
		QAuthMenuEntity authMenu = QAuthMenuEntity.authMenuEntity;
		QAuthUserEntity authUser = QAuthUserEntity.authUserEntity;

		BooleanExpression exp = null;
		List<Long> authList = new ArrayList<>();

		// 최초관리자(admin)는 모든 메뉴 보이도록 조건절 없앰
		if (usrNo > 1) {
			authList = queryFactory
					.select(authUser.auth.authId)
					.from(authUser)
					.where(authUser.user.usrNo.eq(usrNo))
					.fetch();
			exp = authMenu.auth.authId.in(authList);
		}

		List<MenuMainResDto> menuList = new ArrayList<>();
		if (usrNo == 1) {
			menuList = queryFactory
					.select(Projections.bean(MenuMainResDto.class,
							menu.parent.menuNm.coalesce(menu.menuNm).as("topMenuName"),
							menu.view.viewPath.as("menuPath"),
							menu.menuNm.as("menuName"),
							menu.menuLevel,
							menu.view.viewPath.substring(1, 4).as("menuGroup"),
							menu.menuSortNo.as("menuOrder")
					)).distinct()
					.from(menu)
					.leftJoin(menu.parent)
					.join(menu.view)
					.where(menu.useYn.eq("Y"))
					.fetch();
		} else if (usrNo > 1) {
			menuList = queryFactory
					.select(Projections.bean(MenuMainResDto.class,
					authMenu.menu.parent.menuNm.coalesce(authMenu.menu.menuNm).as("topMenuName"),
					authMenu.menu.view.viewPath.as("menuPath"),
					authMenu.menu.menuNm.as("menuName"),
					authMenu.menu.menuLevel,
					authMenu.menu.view.viewPath.substring(1, 4).as("menuGroup"),
					authMenu.menu.menuSortNo.as("menuOrder")
					)).distinct()
					.from(authMenu)
					.join(authMenu.menu)
					.leftJoin(authMenu.menu.parent)
					.join(authMenu.menu.view)
					.where(exp.and(authMenu.menu.useYn.eq("Y")))
					.fetch();
		}
		
		return menuList;
	}

}
