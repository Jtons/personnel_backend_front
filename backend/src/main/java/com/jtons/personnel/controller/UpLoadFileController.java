package com.jtons.personnel.controller;


import com.alibaba.fastjson.JSON;
import com.jtons.personnel.res.ResFileResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/upload")
public class UpLoadFileController extends BaseUpLoadController{
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Value("${file-save-path}")
    private String fileSavePath;

    @ResponseBody
    @RequestMapping("/files")
    public void uploadPicture(@RequestParam(value = "avatar",required = false)MultipartFile file, HttpServletRequest request, HttpServletResponse response){
    ResFileResult resFileResult=new ResFileResult();
    Map<String,Object> map=new HashMap<String,Object>();
    File targetFile=null;
    String url="";//返回存储路径
    int code=1;
    logger.info("file=", file);
        String fileName="";
    try{
         fileName=file.getOriginalFilename();//获取文件名加后缀
    }catch (Exception e){
        e.printStackTrace();;
        resFileResult.setMessage("系统异常，图片上传失败");
    }

    if(fileName!=null&&fileName!=""){
//        String returnUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/imgs";//存储路径
//        String path=request.getSession().getServletContext().getRealPath("upload/imgs");//文件存储位置
        String returnUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/images";//存储路径
        String path=fileSavePath;//文件存储位置
        String fileF=fileName.substring(fileName.lastIndexOf("."),fileName.length());//文件后缀
        fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
        //先判断文件是否存在
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String fileAdd= sdf.format(new Date());
        //获取文件夹路径
        File file1=new File(path+"/"+fileAdd);
        logger.info("file1="+file1+"\nfileAdd="+fileAdd+"\npath="+path);
        //如果文件夹不存在则创建
        if(!file1.exists()&&!file1.isDirectory()){
            file1.mkdirs();
        }
        //将图片存入文件夹
        targetFile=new File(file1,fileName);
        try{
            //将上传的文件写到服务器上指定的文件夹内
            logger.info("returnUrl="+returnUrl+"\nfileAdd="+fileAdd+"\nfileName="+fileName+"\ntargetFile="+targetFile);
            file.transferTo(targetFile);
            url=returnUrl+"/"+fileAdd+"/"+fileName;
            code=0;
            resFileResult.setResult(map);
            resFileResult.setMessage(url);
        }catch (Exception e){
            e.printStackTrace();;
            resFileResult.setMessage("系统异常，图片上传失败");
        }
    }
    writeJson(response,resFileResult);
}
}
