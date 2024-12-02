package com.meta.sys.auth.infrastructure.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.auth.infrastructure.SysAuthRepositoryCustom;
import com.meta.sys.auth.infrastructure.entity.AuthEntity;
import com.meta.sys.auth.infrastructure.entity.QAuthEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SysAuthRepositoryCustomImpl implements SysAuthRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

	@Override
	public Page<AuthEntity> selectAuthList(SearchDto searchDto, Pageable pageable) {
		
		String searchTyp = searchDto.getSearchTyp();
        String searchWrd = searchDto.getSearchWrd();
		String useYn = searchDto.getUseYn();
		QAuthEntity auth = QAuthEntity.authEntity;

		BooleanBuilder builder = new BooleanBuilder();
		
		BooleanBuilder booleanBuilder1 = new BooleanBuilder();
        if (!StringUtils.isBlank(searchWrd)) {
			if (searchTyp.contains("a")) booleanBuilder1.or(auth.authCd.upper().contains(searchWrd.toUpperCase()));
            if (searchTyp.contains("b")) booleanBuilder1.or(auth.authNm.upper().contains(searchWrd.toUpperCase()));
		}
		builder.and(booleanBuilder1);

        if (!StringUtils.isBlank(useYn)) builder.and(auth.useYn.eq(searchDto.getUseYn()));
		
		List<AuthEntity> authList = queryFactory
				.selectFrom(auth)
				.where(builder)
				.orderBy(auth.authId.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		JPAQuery<Long> countQuery = queryFactory
				.select(auth.count())
				.from(auth)
				.where(builder);
		
		return PageableExecutionUtils.getPage(authList, pageable, countQuery::fetchOne );
	}
}
