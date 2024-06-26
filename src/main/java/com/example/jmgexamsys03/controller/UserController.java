package com.example.jmgexamsys03.controller;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.LoginDto;
import com.example.jmgexamsys03.entity.Dto.RegisterUserDto;
import com.example.jmgexamsys03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseResult registerController(@RequestBody RegisterUserDto registerUserDto){
        return userService.registerUser(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseResult loginController(@RequestBody LoginDto loginDto){
        return userService.loginUser(loginDto);
    }

    @GetMapping ("/info")
    public ResponseResult getInfoController(){
        return userService.getinfoUser();
    }

    @PostMapping("/logout")
    public ResponseResult  logoutController(){
        return ResponseResult.okResult("成功退出登录");
    }
}
