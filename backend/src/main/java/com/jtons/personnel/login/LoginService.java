package com.jtons.personnel.login;


import com.jtons.personnel.res.ResResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    public ResResult login(LoginVo loginVo);

    ResResult getUserInfo(HttpServletRequest request, HttpServletResponse response, Object handler);
}
