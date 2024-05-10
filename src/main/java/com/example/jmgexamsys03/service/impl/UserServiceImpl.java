package com.example.jmgexamsys03.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
import com.example.jmgexamsys03.entity.Dto.LoginDto;
import com.example.jmgexamsys03.entity.Dto.RegisterUserDto;
import com.example.jmgexamsys03.entity.User;
import com.example.jmgexamsys03.mapper.UserMapper;
import com.example.jmgexamsys03.service.UserService;

import com.example.jmgexamsys03.utils.JwtUtils;
import com.example.jmgexamsys03.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private RedisCache redisCache;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     *
     * @param registerUserDto
     * @return
     * 处理注册相关事务，这里只是一个用于测试的实现，需要补充其余东西
     */
    @Override
    public ResponseResult registerUser(RegisterUserDto registerUserDto) {
        User user = new User(
                registerUserDto.getUsername(),
                registerUserDto.getPassword(),
                registerUserDto.getFullname()
        );

        System.out.println("&&& 运行到创建对象了");
        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult loginUser(LoginDto loginDto) {
        System.out.println("登录信息为" + loginDto);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",loginDto.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);

        if(user == null){
            // 用户不存在
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        return ResponseResult.okResult();
    }

    /**
     * 验证token的逻辑
     * @param token
     * @return
     */
    @Override
    public User checkToken(String token) {
        System.out.println("check token:"+token);
        if(StringUtils.isEmpty(token)){
            return null;
        }
        Map<String,Object> map = JwtUtils.checkToken(token);
        if(map==null){
            return null;
        }
        String userJson =  redisCache.getCacheObject("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }
}
