package com.meta.sys.message.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.cmm.dto.ResponseDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.sys.message.controller.port.SysMessageService;
import com.meta.sys.message.controller.request.MessageRequest.MessageCreate;
import com.meta.sys.message.controller.request.MessageRequest.MessageSelect;
import com.meta.sys.message.controller.request.MessageRequest.MessageUpdate;
import com.meta.sys.message.controller.response.MessageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys/message")
@RequiredArgsConstructor
public class SysMessageController {
    
    private final SysMessageService sysMessageService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseDto> list(MessageSelect dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.builder()
                .data(sysMessageService.list(dto).map(d -> MessageResponse.from(d)))
                .build());
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ResponseDto> all() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDto.builder()
                .data(sysMessageService.all().stream().map(d -> MessageResponse.from(d)).collect(Collectors.toList()))
                .build());
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDto> save(@RequestBody MessageCreate dto) throws BusinessException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.builder()
                .data(sysMessageService.save(dto))
                .build());
    }

    @PutMapping(value = "/save")
    public ResponseEntity<ResponseDto> update(@RequestBody MessageUpdate dto) throws BusinessException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.builder()
                .data(sysMessageService.update(dto))
                .build());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ResponseDto> get(MessageSelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDto.builder()
                .data(sysMessageService.get(dto))
                .build());
    }
}
