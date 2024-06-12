package com.example.jmgexamsys03.config;

import com.example.jmgexamsys03.handle_interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 * WebMvcConfigurer配置类其实是Spring内部的一种配置方式，采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制，
 * 可以自定义一些Handler，Interceptor，ViewResolver，MessageConverter。(摘自CSDN)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    public WebMvcConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;

    }

    private LoginInterceptor loginInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         * 配置跨域请求
         */
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowCredentials(true) //允许发送Cookie
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 使用拦截器
         */
        // 检查前端传送回来的数据是否有对应的token
        // 检查所有的接口，但是除了注册和登录接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/user/register","/user/login","/downloadEPaper","/user/logout");//参数是需要做登录验证的接口，这里代表验证所有/开头的接口。

    }

    // 用虚拟路径进行映射，使得前端可以调用本地的代码
    // 部署到云上时也许需要删除
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 资源映射路径
         * addResourceHandler：访问映射路径
         * addResourceLocations：资源绝对路径
         */

        // 部署时似乎不需要更改，访问时如果是http://localhost:8087/mfile/...就自动访问后面的addResourceLocations路径下的文件夹
        // 这里的image并不真实存在，只是用于逻辑区分或者代指后面的绝对路径
        registry.addResourceHandler("/mfile/**").addResourceLocations("file:/usr/app/mfile/");
    }
}

