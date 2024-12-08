package com.meta.sys.message.controller.request;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.message.domain.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class MessageRequest {
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class MessageSelect extends SearchDto {
        private long msgId;
        private String msgCd;
        private String msgCn;
        private String delYn;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MessageCreate {
        private String msgCd;
        private String msgCn;
        private String delYn;

        public Message to() {
            return Message.builder()
                .msgCd(msgCd)
                .msgCn(msgCn)
                .delYn(delYn)
                .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MessageUpdate {
        private long msgId;
        private String msgCn;
        private String delYn;

        public Message to() {
            return Message.builder()
                .msgId(msgId)
                .msgCn(msgCn)
                .delYn(delYn)
                .build();
        }
    }
}
