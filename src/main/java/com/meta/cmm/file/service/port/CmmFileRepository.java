package com.meta.cmm.file.service.port;

import java.util.List;
import java.util.Optional;

import com.meta.cmm.file.domain.AttachFile;
import com.meta.cmm.file.domain.AttachFile.AttachFilePK;

public interface CmmFileRepository {

    /* Query Method */
    List<AttachFile> findByAtchFileIdAndUseYn(long atchFileId, String useYn);
    Optional<AttachFile> findFirstByAtchFileIdOrderByFileSnDesc(long atchFileId);
    Optional<AttachFile> findFirstByOrderByAtchFileIdDesc();
    
    /* Query Dsl */

    /* JpaRepository */
    Optional<AttachFile> findById(AttachFilePK attachFilePK);
    // Optional<AttachFile> findById(AttachFile attachFile);
    AttachFile save(AttachFile delFileVo);
    List<AttachFile> saveAll(List<AttachFile> fileVos);
}