package com.meta.sys.message.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.sys.message.domain.Message;
import com.querydsl.core.BooleanBuilder;

public interface SysMessageRepository {
    
    Page<Message> findAll(BooleanBuilder booleanBuilder, Pageable pageable);
    List<Message> findAllByOrderByMsgId();
    List<Message> findAllByDelYnOrderByMsgCd(String delYn);
    Optional<Message> findAllByMsgCd(String msgCd);
    Message findByMsgCdAndLocale(String messageCd, String locale);
    Optional<Message> findByMsgCd(String msgCd);

    List<Message> findAll();
    Optional<Message> findById(long msgId);
    Message save(Message message);
}
