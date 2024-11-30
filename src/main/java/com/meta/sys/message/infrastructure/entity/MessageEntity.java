package com.meta.sys.message.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.meta.cmm.entity.BaseEntity;
import com.meta.sys.message.domain.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "SYS007MT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class MessageEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long msgId;

    @Column
    private String msgCd;
    
    @Column
    private String msgCn;
    
    @Column
    private String locale;
    
    @Column
    private String delYn;
    
    public Message to() {
        return Message.builder()
            .msgId(msgId)
            .msgCd(msgCd)
            .msgCn(msgCn)
            .locale(locale)
            .delYn(delYn)
            .build();
    }
}
