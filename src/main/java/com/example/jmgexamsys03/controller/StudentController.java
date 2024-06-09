package com.example.jmgexamsys03.controller;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/getExaml")
    public ResponseResult getSExam(){
        return studentService.queryExams();
    }
}
