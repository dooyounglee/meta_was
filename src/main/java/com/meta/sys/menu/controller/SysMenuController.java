package com.meta.sys.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.meta.cmm.exception.BusinessException;
import com.meta.sys.menu.controller.port.SysMenuService;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuCreate;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuSelect;
import com.meta.sys.menu.controller.request.SysMenuRequest.MenuUpdate;
import com.meta.sys.menu.controller.response.SysMenuResponse;
import com.meta.sys.menu.domain.Menu;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {
    
    private final SysMenuService sysMenuService;

    @GetMapping(value = "/selectMenuList")
    public ResponseEntity<ResponseDto> selectMenuList(SearchDto searchDto, @PageableDefault Pageable pageable) {
    	
    	Page<Menu> data = sysMenuService.selectMenuList(searchDto, pageable);
    	
    	ResponseDto responseDto = ResponseDto.builder()
    	    	.data(data)
    	    	.build();
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping(value = "/selectMenuListAll")
    public ResponseEntity<ResponseDto> selectMenuListAll() {
    	
    	List<Menu> data = sysMenuService.selectMenuListAll();
    	
    	ResponseDto responseDto = ResponseDto.builder()
    	    	.data(data)
    	    	.build();
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    
    @PostMapping(value = "/insertMenu")
    public ResponseEntity<ResponseDto> insertMenu(@Valid @RequestBody MenuCreate menuCreateDto) {
    	
    	Menu menu = sysMenuService.insertMenu(menuCreateDto);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(menu)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}
    
    @PutMapping(value = "/updateMenu")
    public ResponseEntity<ResponseDto> updateMenu(@Valid @RequestBody MenuUpdate menuUpdateDto) {
    	
    	Menu menu = sysMenuService.updateMenu(menuUpdateDto);
    	SysMenuResponse result = SysMenuResponse.toDtoNoChildren(menu);
    	
    	ResponseDto responseDto = ResponseDto.builder()
	    	.data(result)
	    	.build();
    	
		return ResponseEntity.ok(responseDto);
	}

    @GetMapping(value = "/searchMenuList")
    public ResponseEntity<ResponseDto> searchMenu(MenuSelect menuSelect) throws BusinessException {
        
		Map<String, Object> data = new HashMap<>();
		List<Menu> list = sysMenuService.searchMenuList(menuSelect);
		
		List<SysMenuResponse> menuList = list.stream()
				.map(m -> SysMenuResponse.toDtoNoChildren(m))
				.collect(Collectors.toList());
		
        data.put("menuList", menuList );
        
        ResponseDto responseDto = ResponseDto.builder()
			.data(data)
			.build();
		
        return ResponseEntity.ok(responseDto);
    }
}
