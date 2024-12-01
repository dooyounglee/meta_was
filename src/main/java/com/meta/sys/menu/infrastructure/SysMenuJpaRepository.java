package com.meta.sys.menu.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meta.sys.menu.infrastructure.entity.MenuEntity;

@Repository
public interface SysMenuJpaRepository extends JpaRepository<MenuEntity, Long>, SysMenuRepositoryCustom {

	//메뉴 계층형으로 조회
	List<MenuEntity> findByUseYnAndParentIsNullOrderByMenuSortNo(String useYn);
}
