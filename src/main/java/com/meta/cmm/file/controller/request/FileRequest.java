package com.meta.cmm.file.controller.request;

import com.meta.cmm.file.domain.AttachFile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileRequest {
    
    private long atchFileId;
    private int fileSn;

    public AttachFile to() {
        return AttachFile.builder()
            .atchFileId(atchFileId)
            .fileSn(fileSn)
            .build();
    }
}
