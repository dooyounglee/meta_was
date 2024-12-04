package com.meta.usr.login.controller.port;

import java.util.Map;

import com.meta.usr.login.controller.request.UsrLoginRequest.UsrLoginLogin;

public interface UsrLoginService {
    
    public Map<String, Object> signIn(UsrLoginLogin loginDto);
    public Map<String, Object> signIntest(UsrLoginLogin loginDto);
}
