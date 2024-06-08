package com.example.jmgexamsys03.controller;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.AddStudentDto;
import com.example.jmgexamsys03.entity.Dto.CheckExamDto;
import com.example.jmgexamsys03.entity.Dto.CreateExamDto;
import com.example.jmgexamsys03.entity.Dto.DeleteStudentDto;
import com.example.jmgexamsys03.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult  DeleteStudentController(@RequestBody DeleteStudentDto deleteStudentDto){
        return teacherService.DeleteStudent(deleteStudentDto);
    }
    @GetMapping("/exam/start")
    public ResponseResult  StartExamController(){
        return teacherService.StartExam();
    }
    @PostMapping("/examing/getInfo")
    public ResponseResult  CheckExamController(@RequestBody CheckExamDto checkExamDto){
        return teacherService.CheckExam(checkExamDto);
    }
    @GetMapping("/exam/end")
    public ResponseResult  EndExamController(){
        return teacherService.EndExam();
    }

    @GetMapping("/exam/getlist")
    public ResponseResult  GetExamController(){
        return teacherService.getExam();
    }

    @GetMapping("/getstu")
    public ResponseResult GetStudentController(){
        return teacherService.getStudent();
    }
}


