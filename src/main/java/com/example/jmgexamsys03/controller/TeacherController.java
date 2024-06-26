package com.example.jmgexamsys03.controller;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.*;
import com.example.jmgexamsys03.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/exam/create")
    public ResponseResult CreateExamController(@RequestBody CreateExamDto createExamDto){
        return teacherService.CreateExam(createExamDto);
    }

//    @PostMapping("/exam/changeInfo")
//    public ResponseResult ChangeExamController(@RequestBody ChangeExamDto changeExamDto){
//        return teacherService.ChangeExam(changeExamDto);
//    }

    @PostMapping("/exam/addStu")
    public ResponseResult  AddStudentController(@RequestBody AddStudentDto addStudentDto){
        return teacherService.AddStudent(addStudentDto);
    }
    @PostMapping("/exam/deleteStu")
    public ResponseResult  DeleteStudentController(@RequestBody DeleteStudentDto dsd){
        return teacherService.DeleteStudent(dsd.getSekeyl());
    }
    @PostMapping("/exam/deleteExam")
    public ResponseResult  DeleteExamController(@RequestBody DeleteExamDto ded){
        return teacherService.DeleteExam(ded.getEidl());
    }
    @PostMapping("/exam/start")
    public ResponseResult  StartExamController(@RequestParam long eid){
        return teacherService.StartExam(eid);
    }
    @PostMapping("/examing/getInfo")
    public ResponseResult  CheckExamController(@RequestBody CheckExamDto checkExamDto){
        return teacherService.CheckExam(checkExamDto);
    }
    @PostMapping("/exam/end")
    public ResponseResult  EndExamController(@RequestParam long eid){
        return teacherService.EndExam(eid);
    }

    @GetMapping("/exam/getlist")
    public ResponseResult  GetExamController(@RequestParam long tid){
        return teacherService.getExam(tid);
    }

    @GetMapping("/getstu")
    public ResponseResult GetStudentController(){
        return teacherService.getStudent();
    }
    @GetMapping("/getestu")
    public ResponseResult GetEStudentController(@RequestParam("eid") String eid){
        System.out.println("收到参数"+eid);
        return teacherService.getEStudent(Long.parseLong(eid));
    }

    @PostMapping ("/exam/getAnsl")
    public ResponseResult GetAnsL(@RequestBody getAnslDto getAnslDto){
        return teacherService.getAnsL(getAnslDto.getSekeyl());
    }
}


