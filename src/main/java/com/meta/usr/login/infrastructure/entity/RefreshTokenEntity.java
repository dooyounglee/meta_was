package com.meta.usr.login.infrastructure.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.meta.cmm.entity.BaseEntity;
import com.meta.usr.login.domain.RefreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "USR003MT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class RefreshTokenEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tknId; // 토큰 번호

    private Long usrNo;
    private String refreshToken;

    public RefreshToken to() {
        return RefreshToken.builder()
            .tknId(tknId)
            .usrNo(usrNo)
            .refreshToken(refreshToken)
            .build();
    }

    public static RefreshTokenEntity from(RefreshToken refreshToken) {
        return RefreshTokenEntity.builder()
            .tknId(refreshToken.getTknId())
            .usrNo(refreshToken.getUsrNo())
            .refreshToken(refreshToken.getRefreshToken())
            .build();
    }
}
