package com.meta.sys.view.domain;

import java.time.LocalDateTime;

import com.meta.sys.view.controller.request.ViewRequest.ViewUpdate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class View {
    
    private Long viewNo;
    private String viewId;
    private String viewNm;
    private String viewDsc;
    private String viewPath;
    private String useYn;

    private LocalDateTime createdDate;

    public View changeView(ViewUpdate updateDto) {
        return View.builder()
            .viewNo(updateDto.getViewNo())  
            .viewId(updateDto.getViewId())
            .viewNm(updateDto.getViewNm())
            .viewDsc(updateDto.getViewDsc())
            .viewPath(updateDto.getViewPath())
            .useYn(updateDto.getUseYn())
            .build();
    }
}
