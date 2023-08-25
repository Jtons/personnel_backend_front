package com.jtons.personnel.service;

import com.jtons.personnel.res.ResResult;
import com.jtons.personnel.vo.DictVo;

public interface DictService {
    ResResult getList(DictVo dictVo);
}
