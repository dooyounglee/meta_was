package com.meta.usr.login.controller.port;

import java.util.Map;

import com.meta.usr.login.controller.request.UsrLoginRequest.UsrLoginLogin;
import com.meta.usr.user.domain.User;

public interface UsrLoginService {
    
    public Map<String, Object> signIn(UsrLoginLogin loginDto);
    public Map<String, Object> signIntest(User user);
}
