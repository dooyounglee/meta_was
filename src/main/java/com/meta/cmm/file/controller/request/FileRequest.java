package com.meta.cmm.file.controller.request;

import com.meta.cmm.dto.FileBaseDto;
import com.meta.cmm.file.domain.AttachFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest extends FileBaseDto {
    
    private long atchFileId;
    private int fileSn;

    public AttachFile to() {
        return AttachFile.builder()
            .atchFileId(atchFileId)
            .fileSn(fileSn)
            .build();
    }
}
