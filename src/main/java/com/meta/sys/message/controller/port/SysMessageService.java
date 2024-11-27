package com.meta.sys.message.controller.port;

import java.util.List;

import org.springframework.data.domain.Page;

import com.meta.sys.message.controller.request.MessageRequest.MessageCreate;
import com.meta.sys.message.controller.request.MessageRequest.MessageSelect;
import com.meta.sys.message.controller.request.MessageRequest.MessageUpdate;
import com.meta.sys.message.domain.Message;

public interface SysMessageService {
    
    public Message getById(long msgId);
    public Page<Message> list(MessageSelect dto);
    public List<Message> all();
    public Message get(MessageSelect dto);
    public Message save(MessageCreate createDto);
    public Message update(MessageUpdate updateDto);
}
