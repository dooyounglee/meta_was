package com.meta.sys.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meta.sys.user.controller.port.SysUserService;
import com.meta.sys.user.controller.request.SysUserRequest;
import com.meta.usr.user.domain.User;
import com.meta.usr.user.infrastructure.entity.QUserEntity;
import com.meta.usr.user.service.port.UsrUserRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    
    public final UsrUserRepository usrUserRepository;

    private QUserEntity user = QUserEntity.userEntity;

    public Page<User> list(SysUserRequest requestDto, Pageable pageable) {
        
        pageable = PageRequest.of(requestDto.getPageIndex()-1, pageable.getPageSize(), Sort.by("usrNo").ascending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);

        return usrUserRepository.findAll(booleanBuilder, pageable);
    }

    private BooleanBuilder getSearch(SysUserRequest requestDto) {

        String searchTyp = requestDto.getSearchTyp();
        String searchWrd = requestDto.getSearchWrd();
        String useYn = requestDto.getUseYn();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // admin은 검색결과에서 제외
        booleanBuilder.and(user.usrId.ne("admin"));

        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
        if (searchWrd != null && searchWrd.trim().length() > 0) {
            if (searchTyp.contains("a")) booleanBuilder1.or(user.usrId.upper().contains(searchWrd.toUpperCase()));
            if (searchTyp.contains("b")) booleanBuilder1.or(user.usrNm.contains(searchWrd));
            if (searchTyp.contains("c")) booleanBuilder1.or(user.usrComp.upper().contains(searchWrd.toUpperCase()));
        }
        booleanBuilder.and(booleanBuilder1);
        
        BooleanBuilder booleanBuilder2 = new BooleanBuilder();
        if (useYn != null && useYn.trim().length() > 0) {
            booleanBuilder2.and(user.useYn.eq(useYn));
        }
        booleanBuilder.and(booleanBuilder2);

        return booleanBuilder;
    }
}
