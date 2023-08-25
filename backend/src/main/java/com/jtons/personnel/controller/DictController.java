package com.jtons.personnel.controller;

import com.jtons.personnel.res.ResResult;
import com.jtons.personnel.service.DictService;
import com.jtons.personnel.vo.DictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dict")
public class DictController {
    @Autowired
    DictService dictService;
    @GetMapping("/list")
    public @ResponseBody
    ResResult getList(DictVo dictVo){
        return dictService.getList(dictVo);
    }
}
