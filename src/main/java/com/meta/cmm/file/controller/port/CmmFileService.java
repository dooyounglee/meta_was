package com.meta.cmm.file.controller.port;

import java.util.List;

import com.meta.cmm.file.domain.AttachFile;

public interface CmmFileService {

    List<AttachFile> list(long atchFileId);
    AttachFile download(AttachFile dto);
}
