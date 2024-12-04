package com.meta.cmm.file.infrastructure.entity;

import java.io.Serializable;

import com.meta.cmm.file.domain.AttachFile;
import com.meta.cmm.file.domain.AttachFile.AttachFilePK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachFilePKEntity implements Serializable {
    
    @Builder.Default
    private long atchFileId = 0;
	private int fileSn;

    public AttachFilePK to() {
        return AttachFilePK.builder()
            .atchFileId(atchFileId)
            .fileSn(fileSn)
            .build();
    }

    public static AttachFilePKEntity from(AttachFilePK attachFilePK) {
        return AttachFilePKEntity.builder()
            .atchFileId(attachFilePK.getAtchFileId())
            .fileSn(attachFilePK.getFileSn())
            .build();
    }

    public static AttachFilePKEntity from(AttachFile attachFile) {
        return AttachFilePKEntity.builder()
            .atchFileId(attachFile.getAtchFileId())
            .fileSn(attachFile.getFileSn())
            .build();
    }

    private static final long serialVersionUID = 1L;
}
