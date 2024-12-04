package com.meta.cmm.file.infrastructure.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.meta.cmm.entity.BaseEntity;
import com.meta.cmm.file.domain.AttachFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "CMM001MT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@IdClass(AttachFilePKEntity.class)
// @DynamicUpdate // 수정한 컬럼만 update 하도록
public class AttachFileEntity extends BaseEntity {

    @Id
    private long atchFileId;
    @Id
    private int fileSn;
    
    private String fileExtsn;
    private long fileMg;
    private String fileStreCours;
    private String originalFileNm;
    private String streFileNm;
    private String useYn;

    public AttachFile to() {
        return AttachFile.builder()
            .atchFileId(atchFileId)
            .fileSn(fileSn)
            .fileExtsn(fileExtsn)
            .fileMg(fileMg)
            .fileStreCours(fileStreCours)
            .originalFileNm(originalFileNm)
            .streFileNm(streFileNm)
            .useYn(useYn)
            .build();
    }

    public static AttachFileEntity from(AttachFile attachFile) {
        return AttachFileEntity.builder()
            .atchFileId(attachFile.getAtchFileId())
            .fileSn(attachFile.getFileSn())
            .fileExtsn(attachFile.getFileExtsn())
            .fileMg(attachFile.getFileMg())
            .fileStreCours(attachFile.getFileStreCours())
            .originalFileNm(attachFile.getOriginalFileNm())
            .streFileNm(attachFile.getStreFileNm())
            .useYn(attachFile.getUseYn())
            .build();
    }
}