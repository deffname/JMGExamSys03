package com.example.jmgexamsys03.handle_interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
import com.example.jmgexamsys03.entity.User;
import com.example.jmgexamsys03.service.UserService;
import com.example.jmgexamsys03.utils.RedisCache;
import com.example.jmgexamsys03.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 登录拦截器。用户登陆后，每发起一个请求都会经过这个拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private UserService userService;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    /**
     * 请求处理前被调用。也就是调用Controller前
     * 如果请求带有Token，检查Token是否正确，如果不正确则向前端返回错误NEED_LOGIN
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        System.out.println("request = " + request.getRequestURI());
        String token = request.getHeader("token");
        String requestURL = String.valueOf(request.getRequestURL());
        System.out.println("本次请求传入的token为"+token);
        // 判断token是否为空
        if(StringUtils.isEmpty(token)){
            System.out.println("error url is " + requestURL);
            throw new Exception(String.valueOf(AppHttpCodeEnum.NEED_LOGIN));
        }
        User user = userService.checkToken(token);
        if(user==null){
            throw new Exception(String.valueOf(AppHttpCodeEnum.NEED_LOGIN));
        }
        System.out.println( "$$$USER = "+user.toString());
        // 将user对象放入UserThreadLocal工具类中，后续该进程可以通过该工具类的get方法获得当前进程的对象
        UserThreadLocal.put(user);

        return true;
    }

    /**
     * 在preHandle,postHandle之后，响应请求之前执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器后续被触发 触发url为"+request.getRequestURI());
        if(request.getRequestURI().equals("/uppic")){
            System.out.println("/uppic结束后对redis数据库里面存储的东西进行修改");
            // 如果刚刚结束的这个接口是上传图片的接口，这意味着redis数据库里面有关avatar的地方需要更新
            redisCache.setCacheObject("TOKEN_"+request.getHeader("token"), JSON.toJSONString(UserThreadLocal.get()),1, TimeUnit.DAYS);
        }
        UserThreadLocal.remove();
    }
}