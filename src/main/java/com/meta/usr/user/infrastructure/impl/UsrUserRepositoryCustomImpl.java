package com.meta.usr.user.infrastructure.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.usr.user.infrastructure.UsrUserRepositoryCustom;
import com.meta.usr.user.infrastructure.entity.QUserEntity;
import com.meta.usr.user.infrastructure.entity.UserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsrUserRepositoryCustomImpl implements UsrUserRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    private QUserEntity user = QUserEntity.userEntity;

    @Override
    public List<UserEntity> selectAllUser(SearchDto searchDto) {
        
        String searchTyp = searchDto.getSearchTyp();
		String searchWrd = searchDto.getSearchWrd();
		
        BooleanBuilder builder = new BooleanBuilder();
        
        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
        if (searchWrd != null && searchWrd.trim().length() > 0) {
            if (searchTyp.contains("a")) booleanBuilder1.or(user.usrId.upper().contains(searchWrd.toUpperCase()));
            if (searchTyp.contains("b")) booleanBuilder1.or(user.usrNm.upper().contains(searchWrd.toUpperCase()));
            if (searchTyp.contains("c")) booleanBuilder1.or(user.usrComp.upper().contains(searchWrd.toUpperCase()));
            // if (searchTyp.contains("c")) booleanBuilder1.or(user.usrPos.upper().contains(searchWrd.toUpperCase()));
        }
        builder.and(booleanBuilder1);

        // 프로젝트 조회 시
        // QInput input = QInput.input;
        // builder.and(user.usrNm.contains(searchDto.getSearchWrd()));
        // if (searchDto.getPjtId() != null && searchDto.getPjtId() > 0) builder.and(user.usrNo.in(
        //                                     JPAExpressions.select(input.user.usrNo)
        //                                     .from(input)
        //                                     .where(input.project.pjtId.eq(searchDto.getPjtId()))
        //                                 ));

		List<UserEntity> userList = queryFactory
				.select(Projections.bean(UserEntity.class,
                    user.usrNo,
                    user.usrNm,
                    user.usrComp,
                    user.usrPos
                ))
                .from(user)
				.where(user.useYn.eq("Y")
                    .and(user.usrId.ne("admin"))
                    .and(builder))
				.orderBy(user.usrNm.asc())
				.fetch();
        
        return userList;
    }
}
