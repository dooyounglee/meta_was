package com.meta.cmm.file.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.cmm.file.infrastructure.entity.AttachFileEntity;
import com.meta.cmm.file.infrastructure.entity.AttachFilePKEntity;

@Repository
public interface CmmFileJpaRepository extends JpaRepository<AttachFileEntity, AttachFilePKEntity> {

    List<AttachFileEntity> findByAtchFileIdAndUseYn(long atchFileId, String useYn);
    Optional<AttachFileEntity> findFirstByOrderByAtchFileIdDesc();
    Optional<AttachFileEntity> findFirstByAtchFileIdOrderByFileSnDesc(long atchFileId);
}
