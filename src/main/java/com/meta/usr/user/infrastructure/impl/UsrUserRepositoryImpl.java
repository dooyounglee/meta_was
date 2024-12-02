package com.meta.usr.user.infrastructure.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.usr.user.domain.User;
import com.meta.usr.user.infrastructure.UsrUserJpaRepository;
import com.meta.usr.user.infrastructure.entity.UserEntity;
import com.meta.usr.user.service.port.UsrUserRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsrUserRepositoryImpl implements UsrUserRepository {

    private final UsrUserJpaRepository usrUserJpaRepository;

    @Override
    public List<User> findAll() {
        return usrUserJpaRepository.findAll()
            .stream().map(UserEntity::to).toList();
    }

    @Override
    public Page<User> findAll(BooleanBuilder booleanBuilder, Pageable pageable) {
        return usrUserJpaRepository.findAll(booleanBuilder, pageable)
            .map(UserEntity::to);
    }

    @Override
    public Optional<User> findByUsrId(String usrId) {
        return usrUserJpaRepository.findByUsrId(usrId).map(UserEntity::to);
    }

    @Override
    public List<User> selectAllUser(SearchDto dto) {
        return usrUserJpaRepository.selectAllUser(dto)
            .stream().map(UserEntity::to).toList();
    }

    @Override
    public Optional<User> findById(long usrNo) {
        return usrUserJpaRepository.findById(usrNo).map(UserEntity::to);
    }

    @Override
    public User save(User user) {
        return usrUserJpaRepository.save(UserEntity.from(user)).to();
    }
    
}
