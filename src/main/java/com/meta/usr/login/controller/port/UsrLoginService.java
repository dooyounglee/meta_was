package com.meta.usr.login.controller.port;

import java.util.Map;

import com.meta.usr.login.controller.request.UsrLoginRequest;
import com.meta.usr.user.domain.User;

public interface UsrLoginService {
    
    public Map<String, Object> signIn(UsrLoginRequest loginDto);

    public Map<String, Object> signIntest(User user);
}
