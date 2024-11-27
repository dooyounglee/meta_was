package com.meta.cmm.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.meta.cmm.util.MessageUtil;

@RestControllerAdvice
public class BusinessExceptionHandler {
    
    @ExceptionHandler(value = { BusinessException.class })
	public ResponseEntity<Object> handlerBusinessException(BusinessException e) {
		e.printStackTrace();
		
		e.setErrorCode(-100);
		e.setMessage(MessageUtil.getMessage(e.getMessageCode(), e.getMessageArgs(), null));
		
		return ResponseEntity
				.status(HttpStatus.EXPECTATION_FAILED)
				.body(e);
	}
	
	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<BusinessException> handlerRuntimeException(RuntimeException e) {
		e.printStackTrace();
		
		BusinessException be = new BusinessException("CM-0001");
		be.setErrorCode(-1);
		be.setMessage("서버에 문제가 발생했습니다. 관리자에게 문의 하세요.");
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(be);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<BusinessException> handlerException(Exception e) {
		e.printStackTrace();
		
		BusinessException be = new BusinessException("CM-0001");
		be.setErrorCode(-1);
		be.setMessage(MessageUtil.getMessage("CM-0001", null, null));
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(be);
	}

	@ExceptionHandler(value = { IOException.class })
	public ResponseEntity<BusinessException> handlerIOException(IOException e) {
		e.printStackTrace();
		
		BusinessException be = new BusinessException("CM-0001");
		be.setErrorCode(-1);
		be.setMessage(MessageUtil.getMessage("CM-0001", null, null));
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(be);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<BusinessException> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		e.printStackTrace();
		
		BusinessException be = new BusinessException("CM-0001");
		be.setErrorCode(-1);
		be.setMessage(e.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage());
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(be);
	}
}
