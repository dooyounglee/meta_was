package com.meta.sys.view.controller.response;

import com.meta.cmm.util.DateUtil;
import com.meta.sys.view.domain.View;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViewResponse {
    
    private Long viewNo;
    private String viewId;
    private String viewNm;
    private String viewDsc;
    private String viewPath;
    private String useYn;
    private String createdYmd;

    public static ViewResponse from(View view) {
        return ViewResponse.builder()
            .viewNo(view.getViewNo())
            .viewId(view.getViewId())
            .viewNm(view.getViewNm())
            .viewDsc(view.getViewDsc())
            .viewPath(view.getViewPath())
            .useYn(view.getUseYn())
            .createdYmd(DateUtil.format(view.getCreatedDate(), "yyyy-MM-dd"))
            .build();
    }
}
