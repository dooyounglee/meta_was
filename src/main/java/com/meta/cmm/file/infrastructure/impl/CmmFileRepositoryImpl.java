package com.meta.cmm.file.infrastructure.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meta.cmm.file.domain.AttachFile;
import com.meta.cmm.file.domain.AttachFile.AttachFilePK;
import com.meta.cmm.file.infrastructure.CmmFileJpaRepository;
import com.meta.cmm.file.infrastructure.entity.AttachFileEntity;
import com.meta.cmm.file.infrastructure.entity.AttachFilePKEntity;
import com.meta.cmm.file.service.port.CmmFileRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CmmFileRepositoryImpl implements CmmFileRepository {
    
    private final CmmFileJpaRepository cmmFileJpaRepository;

    @Override
    public List<AttachFile> findByAtchFileIdAndUseYn(long atchFileId, String useYn) {
        return cmmFileJpaRepository.findByAtchFileIdAndUseYn(atchFileId, useYn)
            .stream().map(AttachFileEntity::to).toList();
    }

    @Override
    public Optional<AttachFile> findFirstByAtchFileIdOrderByFileSnDesc(long atchFileId) {
        return cmmFileJpaRepository.findFirstByAtchFileIdOrderByFileSnDesc(atchFileId)
            .map(AttachFileEntity::to);
    }

    @Override
    public Optional<AttachFile> findFirstByOrderByAtchFileIdDesc() {
        return cmmFileJpaRepository.findFirstByOrderByAtchFileIdDesc()
            .map(AttachFileEntity::to);
    }

    @Override
    public Optional<AttachFile> findById(AttachFilePK attachFilePK) {
        return cmmFileJpaRepository.findById(AttachFilePKEntity.from(attachFilePK))
            .map(AttachFileEntity::to);
    }

    /*@Override
    public Optional<AttachFile> findById(AttachFile attachFile) {
        return cmmFileJpaRepository.findById(AttachFilePKEntity.from(attachFile))
            .map(AttachFileEntity::to);
    }*/

    @Override
    public AttachFile save(AttachFile delFileVo) {
        return cmmFileJpaRepository.save(AttachFileEntity.from(delFileVo))
            .to();
    }

    @Override
    public List<AttachFile> saveAll(List<AttachFile> fileVos) {
        return cmmFileJpaRepository.saveAll(fileVos.stream().map(AttachFileEntity::from).toList())
            .stream().map(AttachFileEntity::to).toList();
    }

    
}
