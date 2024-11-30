package com.meta.sys.view.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.sys.view.domain.View;

@Repository
public interface SysViewJpaRepository extends JpaRepository<View, Long>, SysViewRepositoryCustom {

	List<View> findByUseYn(String useYn);
	Long countByViewPath(String viewPath);
}
