package com.example.jmgexamsys03.service;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.LoginDto;
import com.example.jmgexamsys03.entity.Dto.RegisterUserDto;
import com.example.jmgexamsys03.entity.User;

public interface UserService {
    /**
     * 处理用户注册
     * @param registerUserDto
     * @return通用响应类型
     */
    ResponseResult registerUser(RegisterUserDto registerUserDto);

    /**
     * 处理用户登录
     * @param loginDto
     * @return 包括一个token和用户的身份
     */

    ResponseResult loginUser(LoginDto loginDto);

    /**
     * 前端切换界面时返回信息
     * @param
     * @return 包括用户名，用户身份，用户头像
     */
    ResponseResult getinfoUser();

    /**
     * 检查用户Token是否正确。如果正确，从token中解析出用户信息并返回
     * @param token
     * @return 从Token中解析出的用户信息
     */
    User checkToken(String token);
}
