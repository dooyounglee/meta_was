package com.meta.sys.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRequest {
    
    private String searchTyp;
    private String searchWrd;
    private int pageIndex;
    private int pageSize;
    private String workScCd;
    private String delYn;
    private String useYn;
}
