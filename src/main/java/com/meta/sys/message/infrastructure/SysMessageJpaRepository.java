package com.meta.sys.message.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.meta.sys.message.infrastructure.entity.MessageEntity;

public interface SysMessageJpaRepository extends JpaRepository<MessageEntity, Long>, QuerydslPredicateExecutor<MessageEntity> {
    
    List<MessageEntity> findAllByOrderByMsgId();
    List<MessageEntity> findAllByDelYnOrderByMsgCd(String delYn);
    Optional<MessageEntity> findAllByMsgCd(String msgCd);
    MessageEntity findByMsgCdAndLocale(String messageCd, String locale);
    Optional<MessageEntity> findByMsgCd(String msgCd);
}
