package com.meta.cmm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseDto {

	// private boolean success;
	private int code; // 응답 http status
	// private String msg;
	private Object data; // 화면에서 쓸 data
	private Object codes; // 화면에서 쓸 공통코드
}
