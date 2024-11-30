package com.meta.sys.view.infrastructure.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.domain.View;
import com.meta.sys.view.infrastructure.SysViewRepositoryCustom;
import com.meta.sys.view.infrastructure.entity.QViewEntity;
import com.meta.sys.view.infrastructure.entity.ViewEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SysViewRepositoryCustomImpl implements SysViewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

    QViewEntity view = QViewEntity.viewEntity;
	
	@Override
	public Page<View> selectViewList(SearchDto searchDto, Pageable pageable) {
		
		List<ViewEntity> viewList = queryFactory
				.selectFrom(view)
				.where(setSearchCondition(searchDto))
				.orderBy(view.viewId.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		List<View> content = viewList.stream()
    			.map(ViewEntity::to)
    			.collect(Collectors.toList());
		
		JPAQuery<Long> countQuery = queryFactory
				.select(view.count())
				.from(view)
				.where(setSearchCondition(searchDto));
		
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne );
	}
	
	@Override
	public List<View> searchViewList(SearchDto searchDto) {
		List<View> list = queryFactory.
				selectFrom(view)
				.where(view.useYn.eq("Y"),
						setSearchCondition(searchDto)
						)
				.fetch()
                .stream().map(ViewEntity::to).collect(Collectors.toList());
		
		return list;
	}
	
	private BooleanBuilder setSearchCondition(SearchDto dto) {

		String searchTyp = dto.getSearchTyp();
		String searchWrd = dto.getSearchWrd();

		BooleanBuilder booleanBuilder1 = new BooleanBuilder();
		
		if (StringUtils.isBlank(searchWrd)) {
			return null;
		}

        if (!StringUtils.isBlank(searchWrd)) {
			if (searchTyp.contains("a")) booleanBuilder1.or(view.viewPath.upper().contains(searchWrd.toUpperCase()));
			if (searchTyp.contains("b")) booleanBuilder1.or(view.viewId.upper().contains(searchWrd.toUpperCase()));
            if (searchTyp.contains("c")) booleanBuilder1.or(view.viewNm.upper().contains(searchWrd.toUpperCase()));
		}
		
		return booleanBuilder1;
	}

}
