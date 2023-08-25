package com.jtons.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jtons.personnel.entity.UserInfo;
import com.jtons.personnel.res.ResResult;
import com.jtons.personnel.service.UserInfoService;
import com.jtons.personnel.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @GetMapping("/list")
    public @ResponseBody
    ResResult getList(){return userInfoService.getList();}
    @GetMapping("/pageList")
    public @ResponseBody
    ResResult getPageList(UserInfoVo userInfoVo, @RequestParam(name="pageNum" , defaultValue = "1")Integer pageNum, @RequestParam(name="pageSize",defaultValue = "10") Integer pageSize){
        Page<UserInfo> page =new Page<UserInfo>(pageNum,pageSize);
        return userInfoService.getPageList(userInfoVo,page);
    }
    @PostMapping("/add")
    public @ResponseBody
    ResResult addUserInfo(@RequestBody UserInfoVo userInfoVo){return userInfoService.addUserInfo(userInfoVo);}
    @PutMapping("/edit")
    public  @ResponseBody
    ResResult editUserInfo(@RequestBody UserInfoVo userInfoVo){return userInfoService.editUserInfo(userInfoVo);}
    @DeleteMapping("/del")
    public @ResponseBody
    ResResult delUserInfo(@RequestBody UserInfoVo userInfoVo){return userInfoService.delUserInfo(userInfoVo.getId());}
}
