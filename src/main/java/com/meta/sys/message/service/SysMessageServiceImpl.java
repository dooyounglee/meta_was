package com.meta.sys.message.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meta.cmm.exception.BusinessException;
import com.meta.sys.message.controller.port.SysMessageService;
import com.meta.sys.message.controller.request.MessageRequest.MessageCreate;
import com.meta.sys.message.controller.request.MessageRequest.MessageSelect;
import com.meta.sys.message.controller.request.MessageRequest.MessageUpdate;
import com.meta.sys.message.domain.Message;
import com.meta.sys.message.service.port.SysMessageRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysMessageServiceImpl implements SysMessageService {
    
    public final SysMessageRepository sysMessageRepository;

    public Message getById(long msgId) {
        return sysMessageRepository.findById(msgId)
            .orElseThrow(() -> new BusinessException("SYS-024")); // SYS-024: 메시지 코드가 존재하지 않습니다.
    }

    public Page<Message> list(MessageSelect dto) {

        Pageable pageable = PageRequest.of(dto.getOffset(), dto.getPageSize(), Sort.by("msgCd").ascending());
        BooleanBuilder booleanBuilder = getSearch(dto);

        return sysMessageRepository.findAll(booleanBuilder, pageable);
    }

    public List<Message> all() {
        return sysMessageRepository.findAllByDelYnOrderByMsgCd("N");
    }

    public Message get(MessageSelect dto) {
        return getById(dto.getMsgId());
    }

    public Message save(MessageCreate createDto) {
        
        // 중복체크
        sysMessageRepository.findByMsgCd(createDto.getMsgCd())
            .ifPresent(m -> {throw new BusinessException("SYS-023");}); // 이미 등록된 메시지코드 입니다.
        return sysMessageRepository.save(createDto.to());
    }

    public Message update(MessageUpdate updateDto) {
        Message message = getById(updateDto.getMsgId());
        message = message.update(updateDto);
        sysMessageRepository.save(message);
        return message;
    }

    private BooleanBuilder getSearch(MessageSelect requestDto) {

        String searchTyp = requestDto.getSearchTyp();
        String searchWrd = requestDto.getSearchWrd();
        // String workScCd = requestDto.getWorkScCd();
        // String delYn = requestDto.getDelYn();

        // QMessage qMessage = QMessage.message;
        
        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
        // if (searchWrd != null && searchWrd.trim().length() > 0) {
        //     if (searchTyp.contains("a")) booleanBuilder1.or(qMessage.msgCd.upper().contains(searchWrd.toUpperCase()));
        //     if (searchTyp.contains("b")) booleanBuilder1.or(qMessage.msgCn.upper().contains(searchWrd.toUpperCase()));
        // }

        // BooleanBuilder booleanBuilder2 = new BooleanBuilder();
        // if (workScCd != null && workScCd.trim().length() > 0) {
        //     booleanBuilder2.and(qMessage.msgCd.startsWith(workScCd));
        // }
        // booleanBuilder1.and(booleanBuilder2);

        // BooleanBuilder booleanBuilder3 = new BooleanBuilder();
        // if (delYn != null && delYn.trim().length() > 0) {
        //     booleanBuilder3.and(qMessage.delYn.eq(delYn));
        // }
        // booleanBuilder1.and(booleanBuilder3);

        return booleanBuilder1;
    }
}
