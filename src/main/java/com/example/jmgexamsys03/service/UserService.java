package com.example.jmgexamsys03.service;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.RegisterUserDto;

public interface UserService {
    /**
     * 处理用户注册
     * @param registerUserDto
     * @return通用响应类型
     */
    ResponseResult registerUser(RegisterUserDto registerUserDto);
}
