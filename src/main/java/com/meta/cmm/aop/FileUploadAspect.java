package com.meta.cmm.aop;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.cmm.dto.FileBaseDto;
import com.meta.cmm.file.controller.port.CmmFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FileUploadAspect {
    private final String EXECUTION = "execution(* com.meta..*.controller.*.*(org.springframework.web.multipart.MultipartHttpServletRequest, ..))";
    
    public final CmmFileService cmmFileService;

    @Around(EXECUTION)
    public Object changeParam(ProceedingJoinPoint joinPoint) throws IOException, IllegalStateException, Throwable {
        log.debug("around");
        
		Object[] modifiedArgs = joinPoint.getArgs();

        // file저장(첫번째 파라미터 MultipartFile[] 타입 일때)
        long atchFileId = -1;
		if (modifiedArgs.length > 0 && modifiedArgs[0] instanceof MultipartHttpServletRequest) {
            atchFileId = cmmFileService.saveAll((MultipartHttpServletRequest) modifiedArgs[0]);
            log.debug("atchFileId: {}", atchFileId);
        }
          
        // // parameter index 찾아서 넣기 
        
        if (modifiedArgs.length > 1 && modifiedArgs[1] instanceof FileBaseDto) {
            ((FileBaseDto) modifiedArgs[1]).setAtchFileId(atchFileId);
        }
        
        return joinPoint.proceed(modifiedArgs);
    }
}
