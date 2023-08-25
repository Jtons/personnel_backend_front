package com.jtons.personnel.login;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jtons.personnel.entity.UserInfo;
import com.jtons.personnel.mapper.UserInfoMapper;
import com.jtons.personnel.res.ResResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
@Service
public class LoginServiceImpl implements LoginService {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    private static String secret="HQ_LAB_WX";
    private static Integer system_if=16;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "HQ_LAB_WX";
    @Resource
    UserInfoMapper userInfoMapper;
    @Override
    public ResResult login(LoginVo loginVo){
       TokenVo tokenVo=new TokenVo();

        if(StringUtils.equals(loginVo.getName(),"admin")){
            System.out.println("特殊用户admin!!!");
            if(!StringUtils.equals(loginVo.getPwd(),"admin")){
                return ResResult.fail("密码错误！");
            }
            tokenVo.setAccess_token(makeToken(loginVo.getName(),loginVo.getPwd(),3600*12));
            tokenVo.setRole("admin");
        }else{

            UserInfo user=userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getName,loginVo.getName()));
            if(user==null){
                return ResResult.fail("账号不存在！");
            }
            if(!StringUtils.equals(user.getPassward(),loginVo.getPwd())){
                return ResResult.fail("密码错误！");
            }
            tokenVo.setAccess_token(makeToken(loginVo.getName(),loginVo.getPwd(),3600*2));
            tokenVo.setRole(user.getRole());
        }
        return ResResult.success(tokenVo);
    }
    private String makeToken(String name,String password,int time ){
        Date createDate = new Date();
        Date expireDate = DateUtils.addSeconds(createDate, time);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        //token创建底层使用的是设计模式中的创建者模式,了解该模式对于下面的代码比较容易理解
        String token = JWT.create().withHeader(header)
                .withClaim("name",name)//playload的一部分:withClaim底层是一个map,可以不断使用链式表达式存数据
                .withIssuedAt(createDate)//创建时间 //playload的一部分
                .withExpiresAt(expireDate) //过期时间 //playload的一部分
                .sign(Algorithm.HMAC256(secret));//生成 signature
        return token;
    }
    @Override
    public ResResult getUserInfo(HttpServletRequest request, HttpServletResponse response, Object handler){
        String url = request.getRequestURL().toString();
        Map map = new HashMap();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        Enumeration headerNames = request.getHeaderNames();


        while (headerNames.hasMoreElements()) {

            String key = (String) headerNames.nextElement();

            String value = request.getHeader(key);

            map.put(key, value);

        }
        String methods = request.getMethod();


        String token = request.getHeader(TOKEN_HEADER);

        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT;
        decodedJWT = jwtVerifier.verify(token.split(" ")[1]);


        String account = decodedJWT.getClaim("name").asString();
        LambdaQueryWrapper<UserInfo> lamb = new LambdaQueryWrapper<UserInfo>();
        lamb.eq(UserInfo::getName,account);
        UserInfo nowUser = userInfoMapper.selectOne(lamb);
        return ResResult.success(nowUser);
    }

}
