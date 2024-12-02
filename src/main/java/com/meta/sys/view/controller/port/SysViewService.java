package com.meta.sys.view.controller.port;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.controller.request.ViewRequest.ViewCreate;
import com.meta.sys.view.controller.request.ViewRequest.ViewUpdate;
import com.meta.sys.view.controller.response.ViewResponse;
import com.meta.sys.view.domain.View;

public interface SysViewService {
    
    public View getView(long viewNo);
    public Page<View> selectViewList(SearchDto searchDto, Pageable pageable);
    public List<View> searchViewList(SearchDto dto);
    public View insertView(ViewCreate viewCreateDto);
    public View updateView(ViewUpdate viewUpdateDto);
    public List<ViewResponse> selectViewAll();
}
