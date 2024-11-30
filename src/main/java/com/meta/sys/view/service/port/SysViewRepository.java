package com.meta.sys.view.service.port;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.sys.view.domain.View;
import com.meta.sys.view.infrastructure.SysViewRepositoryCustom;

@Repository
public interface SysViewRepository extends JpaRepository<View, Long>, SysViewRepositoryCustom {
	
	List<View> findByUseYn(String useYn);
	Long countByViewPath(String viewPath);
}
