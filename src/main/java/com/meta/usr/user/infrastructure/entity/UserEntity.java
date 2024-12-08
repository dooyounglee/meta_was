package com.meta.usr.user.infrastructure.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.meta.cmm.entity.BaseEntity;
import com.meta.usr.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "USR001MT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class UserEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usrNo; // 사용자 번호

    @Column(nullable = false)
    private String usrId; // 사용자ID
    
    // @Column(columnDefinition = "VARCHAR(255) COLLATE \"ko-KR-x-icu\" ")
    private String usrNm; // 사용자명
    private String usrPw; // 사용자비번
    private String useYn; // 사용여부
    private String usrComp; // 수행사명
    private String usrPos; // 직위
    private String usrPhone; // 연락처
    private String usrPhoto; // 프로필사진

    @Builder.Default
    @CollectionTable(name="USR002MT")
    @ElementCollection
    private List<String> roles = new ArrayList<>();

    public User to() {
        return User.builder()
            .usrNo(usrNo)
            .usrId(usrId)
            .usrNm(usrNm)
            .usrPw(usrPw)
            .useYn(useYn)
            .usrComp(usrComp)
            .usrPos(usrPos)
            .usrPhone(usrPhone)
            .usrPhoto(usrPhoto)
            .roles(roles)
            .build();
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
            .usrNo(user.getUsrNo())
            .usrId(user.getUsrId())
            .usrNm(user.getUsrNm())
            .usrPw(user.getUsrPw())
            .useYn(user.getUseYn())
            .usrComp(user.getUsrComp())
            .usrPos(user.getUsrPos())
            .usrPhone(user.getUsrPhone())
            .usrPhoto(user.getUsrPhoto())
            .roles(user.getRoles())
            .build();
    }
}
