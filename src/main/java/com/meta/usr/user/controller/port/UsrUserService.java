package com.meta.usr.user.controller.port;

import java.util.List;

import com.meta.cmm.dto.SearchDto;
import com.meta.cmm.exception.BusinessException;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserChangePw;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserCreate;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserPhoto;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserReset;
import com.meta.usr.user.controller.request.UsrUserRequest.UsrUserUpdate;
import com.meta.usr.user.domain.User;

public interface UsrUserService {

    public User create(UsrUserCreate createDto) throws BusinessException;
    public User update(UsrUserUpdate updateDto) throws BusinessException;
    public User reset(UsrUserReset requestDto);
    public long getUsrNo();
    public User getUser();
    public User getUser(long usrNo);
    public User getUser(String usrId);
    public boolean hasAuth(String... auth);
    public User getUserWithRole(long usrNo);
    public List<User> all();
    public List<User> selectAllUser(SearchDto dto);
    public User changePassword(UsrUserChangePw changePwDto);
    public User photo(UsrUserPhoto photoDto);
}
