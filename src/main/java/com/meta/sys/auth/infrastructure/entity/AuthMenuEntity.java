package com.meta.sys.auth.infrastructure.entity;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.cmm.entity.BaseEntity;
import com.meta.sys.auth.domain.AuthMenu;
import com.meta.sys.menu.infrastructure.entity.MenuEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "SYS004LT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"auth","menu"}, callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class AuthMenuEntity extends BaseEntity {

    /** 권한-메뉴ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authMenuId;

    /** 권한 */
    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auth_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private AuthEntity auth;

    /** 메뉴ID */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MenuEntity menu;

	public AuthMenu to() {
        return AuthMenu.builder()
            .authMenuId(authMenuId)
            .auth(auth.to())
            .menu(menu.to())
            .build();
    }

    public static AuthMenuEntity from(AuthMenu authMenu) {
        return AuthMenuEntity.builder()
            .authMenuId(authMenu.getAuthMenuId())
            .auth(AuthEntity.from(authMenu.getAuth()))
            .menu(MenuEntity.from(authMenu.getMenu()))
            .build();
    }
}
