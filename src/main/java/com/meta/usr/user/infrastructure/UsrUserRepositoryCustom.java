package com.meta.usr.user.infrastructure;

import java.util.List;

import com.meta.cmm.dto.SearchDto;
import com.meta.usr.user.infrastructure.entity.UserEntity;

public interface UsrUserRepositoryCustom {
    
    List<UserEntity> selectAllUser(SearchDto searchDto);
}
