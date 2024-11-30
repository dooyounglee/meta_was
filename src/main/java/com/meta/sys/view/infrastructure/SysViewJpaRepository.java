package com.meta.sys.view.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.sys.view.infrastructure.entity.ViewEntity;

@Repository
public interface SysViewJpaRepository extends JpaRepository<ViewEntity, Long>, SysViewRepositoryCustom {

	List<ViewEntity> findByUseYn(String useYn);
	Long countByViewPath(String viewPath);
}
