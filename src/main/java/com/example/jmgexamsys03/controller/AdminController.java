package com.example.jmgexamsys03.controller;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.SetPwdDto;
import com.example.jmgexamsys03.entity.Dto.setEStateDto;
import com.example.jmgexamsys03.entity.Dto.setPreETimeDto;
import com.example.jmgexamsys03.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllUser")
    public ResponseResult getAUsrController(){
        return adminService.queryAllUser();
    }

    @GetMapping("/getAllExam")
    public ResponseResult getAExamController(){
        return adminService.getAllExam();
    }

    @PostMapping("/updatepwd")
    public ResponseResult updatePwdController(@RequestBody SetPwdDto setPwdDto){
        return adminService.resetPassword(setPwdDto.getUid(),setPwdDto.getNewpwd());
    }

    @PostMapping("/setSTime")
    public ResponseResult setTimeController(@RequestBody setPreETimeDto setPreETimeDto){
        return adminService.setETime(setPreETimeDto);
    }

    @PostMapping("/setstate")
    public ResponseResult setEState(@RequestBody setEStateDto setEStateDto){
        return adminService.setEState(setEStateDto.getEid(),setEStateDto.getState());
    }
}
