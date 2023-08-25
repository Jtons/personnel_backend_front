package com.jtons.personnel.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jtons.personnel.entity.Dict;
import com.jtons.personnel.mapper.DictMapper;
import com.jtons.personnel.res.ResResult;
import com.jtons.personnel.service.DictService;
import com.jtons.personnel.vo.DictVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DictServiceImpl implements DictService {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource
    DictMapper dictMapper;
    public ResResult getList(DictVo dictVo){
        logger.info("【字典信息】--[获取列表]--dictVo="+ JSON.toJSONString(dictVo));
        LambdaQueryWrapper<Dict> lamb=new LambdaQueryWrapper<Dict>();
        lamb.isNull(Dict::getDelectAt);
        if(dictVo.getType()!=null){
            lamb.eq(Dict::getType,dictVo.getType());
        }
        return ResResult.success(dictMapper.selectList(lamb));
    }
}
