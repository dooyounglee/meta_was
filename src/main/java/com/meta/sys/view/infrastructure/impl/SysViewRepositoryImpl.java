package com.meta.sys.view.infrastructure.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.domain.View;
import com.meta.sys.view.infrastructure.SysViewJpaRepository;
import com.meta.sys.view.infrastructure.entity.ViewEntity;
import com.meta.sys.view.service.port.SysViewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SysViewRepositoryImpl implements SysViewRepository {

    private final SysViewJpaRepository sysViewJpaRepository;

    @Override
    public List<View> findByUseYn(String useYn) {
        return sysViewJpaRepository.findByUseYn(useYn)
            .stream().map(ViewEntity::to).toList();
    }

    @Override
    public Long countByViewPath(String viewPath) {
        return sysViewJpaRepository.countByViewPath(viewPath);
    }

    @Override
    public Page<View> selectViewList(SearchDto searchDto, Pageable pageable) {
        return sysViewJpaRepository.selectViewList(searchDto, pageable)
            .map(ViewEntity::to);
    }

    @Override
    public List<View> searchViewList(SearchDto dto) {
        return sysViewJpaRepository.searchViewList(dto)
            .stream().map(ViewEntity::to).toList();
    }

    @Override
    public View save(View view) {
        return sysViewJpaRepository.save(ViewEntity.from(view)).to();
    }

    @Override
    public Optional<View> findById(Long viewNo) {
        return sysViewJpaRepository.findById(viewNo).map(ViewEntity::to);
    }
    
}
