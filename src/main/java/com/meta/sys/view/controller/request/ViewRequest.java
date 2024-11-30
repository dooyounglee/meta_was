package com.meta.sys.view.controller.request;

import com.meta.sys.view.domain.View;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ViewRequest {
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewSelect {
        private long msgId;
        private int offset;
        private int pageSize;
        private String searchTyp;
        private String searchWrd;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewCreate {
        private String viewId;
        private String viewNm;
        private String viewDsc;
        private String viewPath;
        private String useYn;

        public View to() {
            return View.builder()
                .viewId(viewId)
                .viewNm(viewNm)
                .viewDsc(viewDsc)
                .viewPath(viewPath)
                .useYn(useYn)
                .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewUpdate {
        private Long viewNo;
        private String viewId;
        private String viewNm;
        private String viewDsc;
        private String viewPath;
        private String useYn;

        public View to() {
            return View.builder()
                .viewNo(viewNo)
                .viewId(viewId)
                .viewNm(viewNm)
                .viewDsc(viewDsc)
                .viewPath(viewPath)
                .useYn(useYn)
                .build();
        }
    }
}
