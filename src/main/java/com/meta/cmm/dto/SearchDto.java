package com.meta.cmm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class SearchDto {
	
	private String searchTyp;
	private String searchWrd;
	private int pageIndex;
    private int pageSize;
	private String useYn;
	private String delYn;
}
