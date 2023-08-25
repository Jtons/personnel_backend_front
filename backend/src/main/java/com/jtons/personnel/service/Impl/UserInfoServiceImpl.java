package com.jtons.personnel.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jtons.personnel.entity.UserInfo;
import com.jtons.personnel.mapper.UserInfoMapper;
import com.jtons.personnel.res.ResResult;
import com.jtons.personnel.service.UserInfoService;
import com.jtons.personnel.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource
    UserInfoMapper userInfoMapper;
    public ResResult getList(){
        logger.info("【用户信息】--[获取所有列表]");
        LambdaQueryWrapper<UserInfo> lamb=new LambdaQueryWrapper<UserInfo>();
        lamb.isNull(UserInfo::getDeleteAt);
        return ResResult.success(userInfoMapper.selectList(lamb));
    }
    @Override
    public ResResult getPageList(UserInfoVo userInfoVo, Page page){
        logger.info("【用户信息】--[获取列表]--搜索值name="+ userInfoVo.getBirthMonth()+";分页值pageNum="+page.getCurrent()+",pageSize="+page.getSize());
        LambdaQueryWrapper<UserInfo> lamb =new LambdaQueryWrapper<UserInfo>();
        lamb.isNull(UserInfo::getDeleteAt);
        if(userInfoVo.getName()!=null){
            lamb.like(UserInfo::getName,userInfoVo.getName());
        }
        if(userInfoVo.getRole()!=null){
            lamb.eq(UserInfo::getRole,userInfoVo.getRole());
        }
        if(userInfoVo.getBirthMonth()!=null){
            lamb.like(UserInfo::getBirthday,"-"+userInfoVo.getBirthMonth()+"-");
        }
        lamb.orderByDesc(UserInfo::getCreateAt);
        Page userInfoPage=userInfoMapper.selectPage(page,lamb);
        List<UserInfo> userInfoList=userInfoPage.getRecords();
        List<UserInfoVo> userInfoVoList=userInfoList.stream().map(item->{
            UserInfoVo userInfoVo1=new UserInfoVo();
            BeanUtils.copyProperties(item,userInfoVo1);
            return userInfoVo1;
        }).collect(Collectors.toList());
        Page<UserInfoVo> userInfoVoPage=new Page<UserInfoVo>(userInfoPage.getCurrent(),userInfoPage.getSize(),userInfoPage.getTotal());
        userInfoVoPage.setRecords(userInfoVoList);
        return ResResult.success(userInfoVoPage);
    }
    @Override
    public ResResult addUserInfo(UserInfoVo userInfoVo){
        logger.info("【用户信息】--[新增]--"+ JSON.toJSONString(userInfoVo));
        LambdaQueryWrapper<UserInfo> lamb=new LambdaQueryWrapper<UserInfo>();
        lamb.isNull(UserInfo::getDeleteAt);
        lamb.eq(UserInfo::getName,userInfoVo.getName());
        if(userInfoMapper.selectCount(lamb)==0){
            UserInfo userInfo=new UserInfo();
            BeanUtils.copyProperties(userInfoVo,userInfo,"id");
            userInfo.setPassward("123456");
            userInfo.setCreateAt(new Timestamp(new Date().getTime()));
            userInfoMapper.insert(userInfo);
            return ResResult.success();
        }else{
            return ResResult.fail("该用户名已存在!");
        }

    }
    @Override
    public ResResult editUserInfo(UserInfoVo userInfoVo){
        logger.info("【用户信息】--[编辑]--"+ JSON.toJSONString(userInfoVo));
        if(userInfoVo.getId()==0){
            return ResResult.fail("id不能为空");
        }
        UserInfo userInfo=userInfoMapper.selectById(userInfoVo.getId());
        BeanUtils.copyProperties(userInfoVo,userInfo);
        userInfo.setUpdateAt(new Timestamp(new Date().getTime()));
        userInfoMapper.updateById(userInfo);
        return ResResult.success();
    }
    @Override
    public ResResult delUserInfo(Long id){
        logger.info("【用户信息】--[删除]--"+ JSON.toJSONString(id));
        UserInfo userInfo=userInfoMapper.selectById(id);
        userInfo.setDeleteAt(new Timestamp(new Date().getTime()));
        Integer res= userInfoMapper.updateById(userInfo);
        if(res==1){
            return ResResult.success();
        }
        return ResResult.fail();
    }

}
