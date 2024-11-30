package com.meta.sys.message.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.sys.message.domain.Message;
import com.meta.sys.message.infrastructure.entity.MessageEntity;
import com.querydsl.core.BooleanBuilder;

public interface SysMessageJpaRepository extends JpaRepository<MessageEntity, Long>, SysMessageRepositoryCustom {
    
    Page<Message> findAll(BooleanBuilder booleanBuilder, Pageable pageable);
    List<MessageEntity> findAllByOrderByMsgId();
    List<MessageEntity> findAllByDelYnOrderByMsgCd(String delYn);
    Optional<MessageEntity> findAllByMsgCd(String msgCd);
    MessageEntity findByMsgCdAndLocale(String messageCd, String locale);
    Optional<MessageEntity> findByMsgCd(String msgCd);
}
