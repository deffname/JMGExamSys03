package com.example.jmgexamsys03.service.impl;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.RegisterUserDto;
import com.example.jmgexamsys03.entity.User;
import com.example.jmgexamsys03.mapper.UserMapper;
import com.example.jmgexamsys03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

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
}
