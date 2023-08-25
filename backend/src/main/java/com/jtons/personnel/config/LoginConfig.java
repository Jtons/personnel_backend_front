package com.jtons.personnel.config;

import com.jtons.personnel.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.core.io.Resource;


;


@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Bean
    public AdminInterceptor getCheckFilter(){
        return  new AdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(getCheckFilter());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/**/templogin",
                "/ticket/detail",
                "/ticket/edit",
                "/auth/login",
                "/auth/loginWx",
                "/**/*.jpg",
                "/**/*.png",
                "/*.html",
                "/**/*.js",
                "/**/*.css",
                "/swagger-resources",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/error",
                "/android/*"
        );
    }
    @Override
    public  void  addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedMethods("POST","GET","PUT","DELETE","OPTIONS");

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //开放static,templates,public 目录 但是请求时候需要加上对应的前缀,比如我访问static下的资源/static/xxxx/xx.js
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

}
