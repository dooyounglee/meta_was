package com.meta.sys.message.controller.response;

import com.meta.sys.message.domain.Message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
    
    private long msgId;
    private String msgCd;
    private String msgCn;
    private String delYn;

    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
            .msgId(message.getMsgId())
            .msgCd(message.getMsgCd())
            .msgCn(message.getMsgCn())
            .delYn(message.getDelYn())
            .build();
    }
}
