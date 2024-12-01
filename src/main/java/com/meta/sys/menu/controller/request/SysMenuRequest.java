package com.meta.sys.menu.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.menu.domain.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@Builder
public class SysMenuRequest {
	
	@Data
	@Builder
	public static class MenuCreate {
		
		/** 메뉴명 */
		@NotBlank(message = "메뉴명은 필수값 입니다.")
	    private String menuNm;

		/** 메뉴레벨 */
		@NotNull(message = "메뉴레벨 필수값 입니다.")
	    private int menuLevel;
		
		/** 메뉴정렬번호 */
		@NotNull(message = "메뉴정렬번호는 필수값 입니다.")
	    private int menuSortNo;
		
		/** 사용여부 */
		@NotBlank(message = "사용여부는 필수값 입니다.")
	    private String useYn;
		
		/** 부모메뉴ID*/
		private Long parentMenuId;
		
		/** 연결화면번호 */
		@NotNull(message = "연결화면번호는 필수값 입니다.")
		private Long viewNo;
		
		public Menu to() {
			return Menu.builder()
					.menuNm(this.menuNm)
					.menuLevel(this.menuLevel)
					.menuSortNo(this.menuSortNo)
					.useYn(this.useYn)
					.build();
		}
	}
	
	@Data
	@Builder
	public static class MenuUpdate {
		/** 메뉴ID */
		@NotNull (message = "메뉴ID는 필수값 입니다.")
	    private Long menuId;
		
		/** 메뉴명 */
		@NotBlank(message = "메뉴명은 필수값 입니다.")
	    private String menuNm;
		
		/** 메뉴레벨 */
		@NotNull(message = "메뉴레벨 필수값 입니다.")
	    private int menuLevel;

		/** 메뉴정렬번호 */
		@NotNull(message = "메뉴정렬번호는 필수값 입니다.")
	    private int menuSortNo;
		
		/** 사용여부 */
		@NotBlank(message = "사용여부는 필수값 입니다.")
	    private String useYn;
		
		/** 부모메뉴ID*/
		private Long parentMenuId;
		
		/** 연결화면번호 */
		@NotNull(message = "연결화면번호는 필수값 입니다.")
		private Long viewNo;
		
		public Menu to() {
			return Menu.builder()
					.menuId(this.menuId)
					.menuNm(this.menuNm)
					.menuLevel(this.menuLevel)
					.menuSortNo(this.menuSortNo)
					.useYn(this.useYn)
					.build();
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class MenuSelect extends SearchDto {
		/** 메뉴레벨 */
	    private Integer menuLevel;
	}
	
}
