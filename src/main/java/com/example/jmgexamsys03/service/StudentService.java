package com.example.jmgexamsys03.service;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Student;

public interface StudentService {

    /**
     * queryExams:(根据学生id查看所对应的考试). <br/>
     * @param
     * @return ResultModel
     */
    ResponseResult queryExams();

    /**
     * displayQuestion:(根据考试的id来获取试卷信息). <br/>
     * @param eId
     * @return ResultModel
     */
    ResponseResult displayQuestion(int eId);


    /**
     * bindIp(开始考试时绑定学生Ip)
     * @param student
     * @return
     */
    ResponseResult bindIp(Student student);
}