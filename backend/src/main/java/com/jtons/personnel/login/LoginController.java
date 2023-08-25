package com.jtons.personnel.login;


import com.jtons.personnel.res.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("auth")
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public @ResponseBody
    ResResult login(@RequestBody LoginVo loginVo){return loginService.login(loginVo);}
    @GetMapping("info")
    public @ResponseBody
    ResResult getInfo(HttpServletRequest request, HttpServletResponse response, Object handler){
        return  loginService.getUserInfo(request,response,handler);
    }
    }
