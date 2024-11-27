package com.meta.cmm.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import com.meta.sys.message.domain.Message;
import com.meta.sys.message.service.port.SysMessageRepository;

@Component("messageSource")
public class CustomMessageSource extends AbstractMessageSource {
    
    private Map<String, MessageFormat> messageMap = new HashMap<>();
	
	@Autowired
	private SysMessageRepository sysMessageRepository;
	
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		MessageFormat messageFormat = this.messageMap.get(code);
		if (messageFormat == null) {
			Optional<Message> oMsgMap = sysMessageRepository.findByMsgCd(code);
			if (oMsgMap.isPresent()) {
				messageMap.put(code, createMessageFormat(oMsgMap.get().getMsgCn(), locale));
				return messageMap.get(code);
			} else {
				return createMessageFormat(code, locale);
			}
		} else {
			return messageMap.get(code);
		}
	}

	@PostConstruct
	private void loadMessage() {
		List<Message> messageList = sysMessageRepository.findAll();
		messageList.stream().forEach(message ->
			this.messageMap.put(message.getMsgCd(), createMessageFormat(message.getMsgCn(), Locale.KOREAN)));
		
	}
	
	public void removeCode(String code, Locale locale) {
		this.messageMap.remove(code);
	}
}
