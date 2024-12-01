package com.meta.sys.menu.controller.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.meta.cmm.util.DateUtil;
import com.meta.sys.menu.domain.Menu;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
public class SysMenuResponse {
	
	/** 메뉴ID */
    private Long menuId;

    /** 메뉴명 */
    private String menuNm;
    
    /** 메뉴레벨 */
    private int menuLevel;
    
    /** 메뉴정렬번호 */
    private int menuSortNo;
    
    /** 사용여부 */
    private String useYn;
    
    /** 생성일자 */
    private String createdYmd;
    
    /** 하위메뉴 */
    @Builder.Default
    private List<SysMenuResponse> children = new ArrayList<>();
    
    /** 부모메뉴ID */
    private Long parentMenuId;
    
    /** 부모메뉴명 */
	private String parentMenuNm;
	
	/** 연결화면번호 */
	private Long viewNo;
	
	/** 연결화면경로 */
	private String viewPath;
    
    /**
    * @methodName    : toDto
    * @author        : KIMHYEOKJIN
    * @description   : Menu(entity -> dto)
    * @param menu
    * @return MenuResDto
    */
    public static SysMenuResponse to(Menu menu) {
    	return SysMenuResponse.builder()
                .menuId(menu.getMenuId())
                .menuNm(menu.getMenuNm())
                .menuLevel(menu.getMenuLevel())
                .menuSortNo(menu.getMenuSortNo())
                .useYn(menu.getUseYn())
                .children(menu.getChildren().stream()
                		.map(SysMenuResponse::to)
                		.collect(Collectors.toList()))
    			.createdYmd(DateUtil.format(menu.getCreatedDate(), "yyyy-MM-dd"))
    			.build();
    }
    
    public static SysMenuResponse toDtoNoChildren(Menu menu) {
    	return SysMenuResponse.builder()
                .menuId(menu.getMenuId())
                .menuNm(menu.getMenuNm())
                .menuLevel(menu.getMenuLevel())
                .menuSortNo(menu.getMenuSortNo())
                .useYn(menu.getUseYn())
    			.createdYmd(DateUtil.format(menu.getCreatedDate(), "yyyy-MM-dd"))
    			.build();
    }

    @QueryProjection
	public SysMenuResponse(Long menuId
			, String menuNm
			, int menuLevel
			, int menuSortNo
			, String useYn
			, LocalDateTime createdYmd
			, Long parentMenuId
			, String parentMenuNm
			, Long viewNo
			, String viewPath
			) {
		this.menuId = menuId;
		this.menuNm = menuNm;
		this.menuLevel = menuLevel;
		this.menuSortNo = menuSortNo;
		this.useYn = useYn;
		this.createdYmd = DateUtil.format(createdYmd, "yyyy-MM-dd");
		this.parentMenuId = parentMenuId;
		this.parentMenuNm = parentMenuNm;
		this.viewNo = viewNo;
		this.viewPath = viewPath;
	}
    
    @Data
	@Builder
    @NoArgsConstructor
    @AllArgsConstructor
	public static class MenuMainResDto {
		
	    private String topMenuName;
        private String menuPath;
        private String menuName;
	    private int menuLevel;
        private String menuGroup;
	    private int menuOrder;
	}
}
