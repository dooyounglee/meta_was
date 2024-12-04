package com.meta.cmm.file.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.cmm.exception.BusinessException;
import com.meta.cmm.file.controller.port.CmmFileService;
import com.meta.cmm.file.controller.request.FileRequest;
import com.meta.cmm.file.controller.response.FileResponse;
import com.meta.cmm.file.domain.AttachFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cmm/file")
@RequiredArgsConstructor
public class CmmFileController {
    
    public final CmmFileService cmmFileService;

    @PostMapping("/upload")
    public List<FileResponse> fileupload(final MultipartHttpServletRequest multiRequest, final FileRequest dto) throws IllegalStateException, IOException {
        
        log.debug("fileDto: {}", dto);
        log.debug("fileDto.getAtchFileId(): {}", dto.getAtchFileId());
        
        return cmmFileService.list(dto.getAtchFileId())
			.stream().map(FileResponse::from).toList();
    }

    @PostMapping("/list")
    public List<FileResponse> list(@RequestBody FileRequest dto) {
        return cmmFileService.list(dto.getAtchFileId()).stream().map(FileResponse::from).toList();
    }

    @GetMapping("/download")
    public void download(FileRequest dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 암호화된 atchFileId 를 복호화 (2022.12.06 추가) - 파일아이디가 유추 불가능하도록 조치
		// long atchFileId = fileDto.getAtchFileId();
		// param_atchFileId = param_atchFileId.replaceAll(" ", "+");
		// byte[] decodedBytes = Base64.getDecoder().decode(param_atchFileId);
		// String decodedFileId = new String(cryptoService.decrypt(decodedBytes,ALGORITM_KEY));
		// int fileSn = fileDto.getFileSn();
		
		AttachFile downloadFileVo = cmmFileService.download(dto.to());

		String fileStreCours = downloadFileVo.getFileStreCours(); // EgovWebUtil.filePathBlackList(fvo.getFileStreCours());
		// String streFileNm = downloadFileVo.getStreFileNm(); // EgovWebUtil.filePathBlackList(fvo.getStreFileNm());

		File uFile = new File(fileStreCours);
		long fSize = uFile.length();

		if (fSize > 0) {
			//String mimetype = "application/x-msdownload";
			String mimetype = "application/x-stuff";

			//response.setBufferSize(fSize);	// OutOfMemeory 발생
			response.setContentType(mimetype);
			//response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fvo.getOrignlFileNm(), "utf-8") + "\"");
			setDisposition(downloadFileVo.getOriginalFileNm(), request, response);
			//response.setContentLength(fSize);

			/*
				* FileCopyUtils.copy(in, response.getOutputStream());
				* in.close();
				* response.getOutputStream().flush();
				* response.getOutputStream().close();
				*/
			
			// Try-with-resources를 이용한 자원 해제 처리 (try 구문에 선언한 리소스를 자동 반납)
			// try에 전달할 수 있는 자원은 java.lang.AutoCloseable 인터페이스의 구현 객체로 한정
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));
					BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());){
					FileCopyUtils.copy(in, out);
				out.flush();
			} catch (FileNotFoundException ex) {
				log.debug("IGNORED: {}", ex.getMessage());
			}

		} else {
			throw new BusinessException("[CMM-012]"); // 파일 크기가 0 입니다.
		}
	}

	@ResponseBody
	@GetMapping("/images")
	public Resource showImage(FileRequest dto) throws MalformedURLException {
		AttachFile downloadFileVo = cmmFileService.download(dto.to());
		return new UrlResource("file:" + downloadFileVo.getFileStreCours());
	}

    /**
	 * 브라우저 구분 얻기.
	 *
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * Disposition 지정하기.
	 *
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			//throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}
}
