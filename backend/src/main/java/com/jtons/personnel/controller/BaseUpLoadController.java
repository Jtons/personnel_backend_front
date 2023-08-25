package com.jtons.personnel.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class BaseUpLoadController {
    protected Logger logger= LoggerFactory.getLogger(getClass());

    /**
     * 输出JSON数据
     * @param response
     * @param jsonStr
     */
    public void writeJson(HttpServletResponse response,String jsonStr){
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw=null;
        try{
            pw=response.getWriter();
            pw.write(jsonStr);
            pw.flush();
        }catch (Exception e){
            logger.info("输出JSON数据异常",e);
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }

    /**
     * 向页面响应JSON字符数组串流
     * @param response
     * @param jsonStr
     * @throws IOException
     */
    public void writeJsonStr(HttpServletResponse response,String jsonStr)throws IOException{
        OutputStream outputStream=null;
        try{
            response.reset();
            response.setCharacterEncoding("UTF-8");
            outputStream=response.getOutputStream();
            outputStream.write(jsonStr.getBytes("UTF-8"));
            outputStream.flush();
        }catch (Exception e){
            logger.info("输出JSON数据异常（writeJsonStr）",e);
        }finally {
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }
    public void writeJsonStr(HttpServletResponse response, InputStream in)throws IOException{
        if(null==in){
            return;
        }
        OutputStream outputStream=null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma","No-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setDateHeader("Expires",0);
            outputStream=response.getOutputStream();
            int len=0;
            byte[] byt=new byte[1024];
            while ((len= in.read(byt))!=-1){
                outputStream.write(byt,0,len);
            }

            outputStream.flush();
        }catch (Exception e){
            logger.info("输出JSON数据异常(writeJsonStr)",e);
        }finally {
            if(outputStream!=null){
                outputStream.close();
                in.close();
            }
        }
    }

    /**
     * 输出JSON数据
     * @param response
     * @param obj
     */
    public void writeJson(HttpServletResponse response,Object obj){
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        Gson gson = new Gson();
        try {
            pw = response.getWriter();
            pw.write(gson.toJson(obj));

            pw.flush();
        } catch (Exception e) {
            logger.info("输出JSON数据异常", e);
        }finally{
            if(pw!=null){
                pw.close();
            }
        }
    }
    public void writeHtml(HttpServletResponse response, String html) {
        response.setContentType("text/html;;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(html);
            pw.flush();
        } catch (Exception e) {
            logger.info("输出HTML数据异常", e);
        }finally{
            if(pw!=null){
                pw.close();
            }
        }
    }

}
