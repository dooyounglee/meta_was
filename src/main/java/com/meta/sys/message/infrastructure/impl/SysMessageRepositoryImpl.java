package com.meta.sys.message.infrastructure.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.sys.message.domain.Message;
import com.meta.sys.message.infrastructure.SysMessageJpaRepository;
import com.meta.sys.message.infrastructure.entity.MessageEntity;
import com.meta.sys.message.service.port.SysMessageRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SysMessageRepositoryImpl implements SysMessageRepository {
    
    private final SysMessageJpaRepository sysMessageJpaRepository;

    @Override
    public Page<Message> findAll(BooleanBuilder booleanBuilder, Pageable pageable) {
        return sysMessageJpaRepository.findAll(booleanBuilder, pageable);
    }

    @Override
    public List<Message> findAllByOrderByMsgId() {
        return sysMessageJpaRepository.findAllByOrderByMsgId()
            .stream().map(MessageEntity::to).toList();
    }

    @Override
    public List<Message> findAllByDelYnOrderByMsgCd(String delYn) {
        return sysMessageJpaRepository.findAllByDelYnOrderByMsgCd(delYn)
            .stream().map(MessageEntity::to).toList();
    }

    @Override
    public Optional<Message> findAllByMsgCd(String msgCd) {
        return sysMessageJpaRepository.findAllByMsgCd(msgCd).map(MessageEntity::to);
    }

    @Override
    public Message findByMsgCdAndLocale(String messageCd, String locale) {
        return sysMessageJpaRepository.findByMsgCdAndLocale(messageCd, locale).to();
    }

    @Override
    public Optional<Message> findByMsgCd(String msgCd) {
        return sysMessageJpaRepository.findByMsgCd(msgCd).map(MessageEntity::to);
    }

    @Override
    public List<Message> findAll() {
        return sysMessageJpaRepository.findAll()
            .stream().map(MessageEntity::to).toList();
    }

    @Override
    public Optional<Message> findById(long msgId) {
        return sysMessageJpaRepository.findById(msgId).map(MessageEntity::to);
    }

    @Override
    public Message save(Message message) {
        return sysMessageJpaRepository.save(MessageEntity.from(message)).to();
    }
}
