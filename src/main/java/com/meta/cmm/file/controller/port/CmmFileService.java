package com.meta.cmm.file.controller.port;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.cmm.file.domain.AttachFile;

public interface CmmFileService {

    List<AttachFile> list(long atchFileId);
    AttachFile download(AttachFile dto);
    long saveAll(MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException, IOException;
}
