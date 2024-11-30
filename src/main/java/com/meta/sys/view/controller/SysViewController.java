package com.meta.sys.view.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.cmm.dto.ResponseDto;
import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.controller.port.SysViewService;
import com.meta.sys.view.controller.request.ViewRequest.ViewCreate;
import com.meta.sys.view.controller.request.ViewRequest.ViewUpdate;
import com.meta.sys.view.controller.response.ViewResponse;
import com.meta.sys.view.domain.View;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys/view")
@RequiredArgsConstructor
public class SysViewController {
    
    private final SysViewService sysViewService;

    @GetMapping(value = "/selectViewList")
    public ResponseEntity<ResponseDto> selectViewList(SearchDto searchDto ,@PageableDefault Pageable pageable) {
    	
    	Page<View> data = sysViewService.selectViewList(searchDto, pageable);
    	
    	ResponseDto responseDto = ResponseDto.builder()
    	    	.data(data)
    	    	.build();
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping(value = "/searchViewList")
    public ResponseEntity<ResponseDto> searchViewList(SearchDto searchDto) {
    	
    	List<View> list = sysViewService.searchViewList(searchDto);
    	
    	List<ViewResponse> data = list.stream()
    			.map(v -> ViewResponse.from(v))
    			.collect(Collectors.toList());
    	
    	ResponseDto responseDto = ResponseDto.builder()
    	    	.data(data)
    	    	.build();
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    
    @PostMapping(value = "/insertView")
    public ResponseEntity<ResponseDto> insertView(@Valid @RequestBody ViewCreate dto) {
    	
    	View view = sysViewService.insertView(dto);
    	ViewResponse result = ViewResponse.from(view);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(result)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}
    
    @PutMapping(value = "/updateView")
    public ResponseEntity<ResponseDto> updateView(@Valid @RequestBody ViewUpdate dto) {
    	
    	View view = sysViewService.updateView(dto);
    	ViewResponse result = ViewResponse.from(view);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(result)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}

}
