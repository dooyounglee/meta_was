package com.meta.cmm.file.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttachFile {
    
    private long atchFileId;
    private int fileSn;

    private String fileExtsn;
    private long fileMg;
    private String fileStreCours;
    private String originalFileNm;
    private String streFileNm;
    private String useYn;

    @Getter
    @Builder
    public static class AttachFilePK {

        private long atchFileId;
        private int fileSn;
    }

    public AttachFilePK toPK() {
        return AttachFilePK.builder()
            .atchFileId(atchFileId)
            .fileSn(fileSn)
            .build();
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
