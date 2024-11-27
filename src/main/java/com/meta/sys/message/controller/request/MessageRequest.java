package com.meta.sys.message.controller.request;

import com.meta.sys.message.domain.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageRequest {
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MessageSelect {
        private long msgId;
        private int offset;
        private int pageSize;
        private String searchTyp;
        private String searchWrd;
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
