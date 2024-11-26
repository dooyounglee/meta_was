package com.meta.cmm.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    
    /** 등록자 */
	@CreatedBy
	@Column(updatable = false)
	private long createdBy;
	
	/** 수정자 */
	@LastModifiedBy
	private long updatedBy;
	
	/** 등록일시 */
	@CreationTimestamp
	@Column(updatable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdDate;
	
	/** 수정일시 */
	@UpdateTimestamp
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime updatedDate;
}
