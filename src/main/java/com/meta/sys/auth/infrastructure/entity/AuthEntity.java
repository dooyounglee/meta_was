package com.meta.sys.auth.infrastructure.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.meta.cmm.entity.BaseEntity;
import com.meta.sys.auth.domain.Auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "SYS003MT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class AuthEntity extends BaseEntity {
    
	/** 분류코드ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    /** 권한코드 */
    private String authCd;

    /** 권한명 */
    private String authNm;
    
    /** 사용여부 */
    private String useYn;

    /** 메뉴목록 */
    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AuthMenuEntity> menuList;

    /** 사용자목록 */
    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AuthUserEntity> userList;

    public Auth to() {
        return Auth.builder()
            .authId(authId)
            .authCd(authCd)
            .authNm(authNm)
            .useYn(useYn)
            .menuList(menuList.stream().map(AuthMenuEntity::to).toList())
            .userList(userList.stream().map(AuthUserEntity::to).toList())
            .build();
    }

    public static AuthEntity from(Auth auth) {
        return AuthEntity.builder()
            .authId(auth.getAuthId())
            .authCd(auth.getAuthCd())
            .authNm(auth.getAuthNm())
            .useYn(auth.getUseYn())
            .menuList(auth.getMenuList().stream().map(AuthMenuEntity::from).toList())
            .userList(auth.getUserList().stream().map(AuthUserEntity::from).toList())
            .build();
    }
}
