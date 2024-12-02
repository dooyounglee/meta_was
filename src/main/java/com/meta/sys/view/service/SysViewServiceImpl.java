package com.meta.sys.view.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.cmm.dto.SearchDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.sys.view.controller.port.SysViewService;
import com.meta.sys.view.controller.request.ViewRequest.ViewCreate;
import com.meta.sys.view.controller.request.ViewRequest.ViewUpdate;
import com.meta.sys.view.controller.response.ViewResponse;
import com.meta.sys.view.domain.View;
import com.meta.sys.view.service.port.SysViewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysViewServiceImpl implements SysViewService {

	private final SysViewRepository sysViewRepository;

	public View getView(long viewNo) {
		return sysViewRepository.findById(viewNo)
			.orElseThrow(() -> new BusinessException("SYS-005")); // 선택된 화면이 없습니다.
	}

	public Page<View> selectViewList(SearchDto searchDto, Pageable pageable) {
		Page<View> list = sysViewRepository.selectViewList(searchDto, pageable);
		return list;
	}
	
	public List<View> searchViewList(SearchDto dto) {
    	List<View> list = sysViewRepository.searchViewList(dto);
    	return list;
	}
	

	public View insertView(ViewCreate viewCreateDto) {
		Long count = sysViewRepository.countByViewPath(viewCreateDto.getViewPath());
		
		if(count > 0) {
			throw new BusinessException("SYS-013"); // 해당 경로가 이미 존재 합니다.
		}
		
		View view = viewCreateDto.to();

		return sysViewRepository.save(view);
	}

	public View updateView(ViewUpdate viewUpdateDto)  {

		Optional<View> result = sysViewRepository.findById(viewUpdateDto.getViewNo());
		if (!result.isPresent()) {
			throw new BusinessException("SYS-026"); // 해당 화면은 존재하지 않습니다.
		}

		Long count = sysViewRepository.countByViewPath(viewUpdateDto.getViewPath());
		View view = result.get();
		
		if(!StringUtils.equals(view.getViewPath(), viewUpdateDto.getViewPath()) && count > 0 ) {
			throw new BusinessException("SYS-013"); // 해당 경로가 이미 존재 합니다.
		}

		view.changeView(viewUpdateDto);

		return view;
	}

	public List<ViewResponse> selectViewAll() {
		List<View> view = sysViewRepository.findByUseYn("Y");
		return view.stream()
    			.map(v -> ViewResponse.from(v))
    			.collect(Collectors.toList());
	}

}
