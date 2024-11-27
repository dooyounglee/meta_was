package com.meta.sys.message.domain;

import com.meta.sys.message.controller.request.MessageRequest.MessageUpdate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Message {
    
    private long msgId;
    private String msgCd;
    private String msgCn;
    private String locale;
    private String delYn;

    public Message update(MessageUpdate updateDto) {
        return Message.builder()
            .msgCn(updateDto.getMsgCn())  
            .delYn(updateDto.getDelYn())
            .build();
    }
}
