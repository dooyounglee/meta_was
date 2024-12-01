package com.meta.sys.menu.infrastructure.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.meta.cmm.entity.BaseEntity;
import com.meta.sys.menu.domain.Menu;
import com.meta.sys.view.infrastructure.entity.ViewEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "SYS002MT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"children", "parent"}, callSuper = true) 
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class MenuEntity extends BaseEntity {
    
	/** 메뉴ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /** 메뉴명 */
    private String menuNm;
    
    /** 메뉴레벨 */
    private int menuLevel;
    
    /** 부모메뉴ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MenuEntity parent;
    
    /** 메뉴정렬번호 */
    private int menuSortNo;
    
    /** 사용여부 */
    private String useYn;
    
    @Builder.Default
    @BatchSize(size = 100)
    @OrderBy("menuSortNo asc")
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<MenuEntity> children= new ArrayList<>();
    
    /** 연결화면*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "view_no", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ViewEntity view;

    public Menu to() {
        return Menu.builder()
            .menuId(menuId)
            .menuNm(menuNm)
            .menuLevel(menuLevel)
            .parent(parent.to())
            .menuSortNo(menuSortNo)
            .useYn(useYn)
            .children(children.stream().map(MenuEntity::to).toList())
            .view(view.to())
            .build();
    }

    public static MenuEntity from(Menu menu) {
        return MenuEntity.builder()
            .menuId(menu.getMenuId())
            .menuNm(menu.getMenuNm())
            .menuLevel(menu.getMenuLevel())
            .parent(MenuEntity.from(menu.getParent()))
            .menuSortNo(menu.getMenuSortNo())
            .useYn(menu.getUseYn())
            .children(menu.getChildren().stream().map(MenuEntity::from).toList())
            .view(ViewEntity.from(menu.getView()))
            .build();
    }
}
