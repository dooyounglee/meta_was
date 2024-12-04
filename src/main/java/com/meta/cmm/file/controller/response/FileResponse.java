package com.meta.cmm.file.controller.response;

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
public class FileResponse extends FileBaseDto {
    
    private int fileSn;
    private String name;
    private long size;
    private String atchFileIdUpload;
    private String path;

    public static FileResponse from(AttachFile attachFile) {
        return FileResponse.builder()
            .atchFileId(attachFile.getAtchFileId())
            .fileSn(attachFile.getFileSn())
            .size(attachFile.getFileMg())
            .name(attachFile.getOriginalFileNm())
            .path(attachFile.getFileStreCours())
            .build();
    }
}
