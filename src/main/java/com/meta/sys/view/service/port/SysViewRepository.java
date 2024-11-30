package com.meta.sys.view.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.meta.cmm.dto.SearchDto;
import com.meta.sys.view.domain.View;

@Repository
public interface SysViewRepository {
	
	/* Query Method */
	List<View> findByUseYn(String useYn);
	Long countByViewPath(String viewPath);
    
	/* Query Dsl */
	Page<View> selectViewList(SearchDto searchDto, Pageable pageable);
    List<View> searchViewList(SearchDto dto);
    
	/* JpaRepository */
	View save(View view);
    Optional<View> findById(Long viewNo);
}
