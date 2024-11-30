package com.meta.sys.view.infrastructure.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.meta.cmm.entity.BaseEntity;
import com.meta.sys.view.domain.View;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "SYS001MT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@ToString(exclude = "cmmCdList", callSuper = true) // 부모 클래스에 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // 부모 클래스에 필드를 포함하는 역할
public class ViewEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewNo;
    
    private String viewId;
    private String viewNm;
    private String viewDsc;
    private String viewPath;
    private String useYn;

    public View to() {
        return View.builder()
            .viewNo(viewNo)
            .viewId(viewId)
            .viewNm(viewNm)
            .viewDsc(viewDsc)
            .viewPath(viewPath)
            .useYn(useYn)
            .build();
    }
}
