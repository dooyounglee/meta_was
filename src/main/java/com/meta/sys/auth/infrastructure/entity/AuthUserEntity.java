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
import com.meta.sys.auth.domain.AuthUser;
import com.meta.usr.user.infrastructure.entity.UserEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "SYS004TT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"auth","user"}, callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class AuthUserEntity extends BaseEntity {
    /** 권한-메뉴ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authUserId;

    /** 메뉴ID */
    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auth_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private AuthEntity auth;

    /** 사용자번호 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usr_no", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UserEntity user;

	public AuthUser to() {
        return AuthUser.builder()
            .authUserId(authUserId)
            .auth(auth.to())
            .user(user.to())
            .build();
    }

    public static AuthUserEntity from(AuthUser authUser) {
        return AuthUserEntity.builder()
            .authUserId(authUser.getAuthUserId())
            .auth(AuthEntity.from(authUser.getAuth()))
            .user(UserEntity.from(authUser.getUser()))
            .build();
    }
}
