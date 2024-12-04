package com.meta.cmm.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.cmm.exception.BusinessException;
import com.meta.cmm.file.controller.port.CmmFileService;
import com.meta.cmm.file.domain.AttachFile;
import com.meta.cmm.file.domain.AttachFile.AttachFilePK;
import com.meta.cmm.file.service.port.CmmFileRepository;
import com.meta.cmm.util.DateUtil;
import com.meta.cmm.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CmmFileServiceImpl implements CmmFileService {
    
    @Value("${meta.upload.path}")
    private String storePathString;

    public final CmmFileRepository cmmFileRepository;

    public AttachFile getAttachFile(AttachFilePK attachFilePK) {
        return cmmFileRepository.findById(attachFilePK)
            .orElseThrow(() -> new BusinessException("[CMM-013]")); // 파일이 없습니다.
    }

    public AttachFile getAttachFile(AttachFile attachFile) {
        return getAttachFile(AttachFilePK.builder().atchFileId(attachFile.getAtchFileId()).fileSn(attachFile.getFileSn()).build());
    }

    public List<AttachFile> list(long atchFileId) {
        // 1이상인 값이 아니라면 빈 배열 리턴
        if (atchFileId <= 0) return new ArrayList<>();
        
        return cmmFileRepository.findByAtchFileIdAndUseYn(atchFileId, "Y");
    }

    public AttachFile download(AttachFile attachFile) {
        return getAttachFile(attachFile);
    }

    public long saveAll(final MultipartHttpServletRequest multiRequest) throws IllegalStateException, IOException {

        // final Map<String, MultipartFile> files = multiRequest.getFileMap();
        final List<MultipartFile> new_files = multiRequest.getFiles("new_files");
        final String[] copy_files = multiRequest.getParameterValues("new_files");
        final String[] del_files = multiRequest.getParameterValues("del_files");
        final String[] web_files = multiRequest.getParameterValues("web_files");

        // 변경할(추가&삭제) 첨부파일 없으면 return
        if ((new_files.size() == 0 && (copy_files == null || (copy_files != null && copy_files.length == 0))) && (del_files == null || (del_files != null && del_files.length == 0))) {
            if (web_files != null && web_files.length > 0) { // 이미 첨부파일이 있다면 쓰던 atchFileId
                return Long.valueOf(web_files[0].split(",")[0]);
            } else { // 이미 첨부했던 파일도 없다면 0
                return 0;
            }
        }
        // del_files 가공
        List<AttachFile> del_files_list = new ArrayList<>();
        if (del_files != null) {
            for (int i=0; i<del_files.length; i++) {
                String[] del_file = del_files[i].split(",");
                del_files_list.add(AttachFile.builder().atchFileId(Long.valueOf(del_file[0])).fileSn(Integer.valueOf(del_file[1])).build());
            }
        }

        // web_files 가공
        List<AttachFile> web_files_list = new ArrayList<>();
        if (copy_files == null && web_files != null) {
            for (int i=0; i<web_files.length; i++) {
                String[] web_file = web_files[i].split(",");
                try {
                    web_files_list.add(AttachFile.builder().atchFileId(Long.valueOf(web_file[0])).fileSn(Integer.valueOf(web_file[1])).build());
                } catch (NumberFormatException e) {
                    log.debug("숫자 변환 중 오류. 건너뜀");
                }
            }
        }

        // copy_files 가공
        List<AttachFile> copy_files_list = new ArrayList<>();
        if (copy_files != null) {
            for (int i=0; i<copy_files.length; i++) {
                String[] copy_file = copy_files[i].split(",");
                try {
                    copy_files_list.add(AttachFile.builder().atchFileId(Long.valueOf(copy_file[0])).fileSn(Integer.valueOf(copy_file[1])).build());
                } catch (NumberFormatException e) {
                    log.debug("숫자 변환 중 오류. 건너뜀");
                }
            }
        }
        
        // atchFileId 정하기
        long atchFileId = 1;
        int fileSn = 1;

        // 이미 atchFileId 있는 경우 있는거 쓰기
        // -1.copy_files없는데 del_files가 있다면 여기꺼 쓰기
        // -2.copy_files없는데 del_files없는데 web_files가 있다면 atchFileId 뒤져서 쓰기
        // -3.copy_files 있거나 새거만 있다면 새로 따기
        if (copy_files_list.size() == 0 && del_files_list.size() > 0) {
            atchFileId = del_files_list.get(0).getAtchFileId();
            Optional<AttachFile> maxFileSn = cmmFileRepository.findFirstByAtchFileIdOrderByFileSnDesc(atchFileId);
            fileSn = maxFileSn.get().getFileSn() + 1;
        } else if (copy_files_list.size() == 0 && web_files_list.size() > 0) {
            for (int i=0; i<web_files_list.size(); i++) {
                if (web_files_list.get(i).getAtchFileId() > 0) {
                    atchFileId = web_files_list.get(i).getAtchFileId();
                    break;
                }
            }
            Optional<AttachFile> maxFileSn = cmmFileRepository.findFirstByAtchFileIdOrderByFileSnDesc(atchFileId);
            fileSn = maxFileSn.get().getFileSn() + 1;
        } else {
            Optional<AttachFile> maxFileVo = cmmFileRepository.findFirstByOrderByAtchFileIdDesc();
            if (maxFileVo.isPresent()) {
                atchFileId = maxFileVo.get().getAtchFileId() + 1;
                fileSn = 1;
            }
        }

        // 추가됄 pk 확인
        log.debug("atchFileId:{} fileSn:{}", atchFileId, fileSn);

        // del_files꺼 삭제
        for (int i=0; i<del_files_list.size(); i++) {
            Optional<AttachFile> del_file = cmmFileRepository.findById(AttachFilePK.builder().atchFileId(del_files_list.get(i).getAtchFileId()).fileSn(del_files_list.get(i).getFileSn()).build());
            if (del_file.isPresent()) {
                AttachFile delFileVo = del_file.get();
                delFileVo.setUseYn("N");
                cmmFileRepository.save(delFileVo);
            }
        }
        
        List<AttachFile> fileVos = new ArrayList<>();
        MultipartFile file;
        String KeyStr = "OTI_";
        
        for (int i=0; i<new_files.size(); i++) {
            file = new_files.get(i);

            String orginFileName = file.getOriginalFilename();
            int index = orginFileName.lastIndexOf(".");
            String fileExt = orginFileName.substring(index + 1);
            long _size = file.getSize();
            String newName = KeyStr + DateUtil.now("yyyyMMddHHmmssSSS") + fileSn;
            String filePath = storePathString + File.separator + DateUtil.now("yyyy/MM/dd") + File.separator + newName;
            FileUtil.makePath(filePath);
            file.transferTo(new File(filePath));

            AttachFile fileVo = AttachFile.builder()
                .atchFileId(atchFileId)
                .fileSn(fileSn)
                .fileExtsn(fileExt)
                .fileMg(_size)
                .fileStreCours(filePath)
                .originalFileNm(orginFileName)
                .streFileNm(newName)
                .useYn("Y")
                .build();
            fileVos.add(fileVo);
            fileSn++;
        }

        if (copy_files_list.size() > 0) {
            List<AttachFile> list = cmmFileRepository.findByAtchFileIdAndUseYn(copy_files_list.get(0).getAtchFileId(), "Y");
            for (int i=0; i<copy_files_list.size(); i++) {
                AttachFile copy_file = copy_files_list.get(i);
                for (AttachFile f : list) {
                    if (f.getAtchFileId() == copy_file.getAtchFileId() && f.getFileSn() == copy_file.getFileSn()) {
                        AttachFile fileVo = AttachFile.builder()
                            .atchFileId(atchFileId)
                            .fileSn(fileSn)
                            .fileExtsn(f.getFileExtsn())
                            .fileMg(f.getFileMg())
                            .fileStreCours(f.getFileStreCours())
                            .originalFileNm(f.getOriginalFileNm())
                            .streFileNm(f.getStreFileNm())
                            .useYn("Y")
                            .build();
                        fileVos.add(fileVo);
                        fileSn++;
                        break;
                    }
                }
            }
        }

        cmmFileRepository.saveAll(fileVos);

        return atchFileId;
    }
}
