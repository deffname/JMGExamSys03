package com.example.jmgexamsys03.service;

import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Student;

public interface StudentService {

    /**
     * login:(学生登录). <br/>
     * @param student
     * @return ResultModel	返回数据模型
     */
    ResponseResult login(Student student);

    /**
     * queryExams:(根据学生id查看所对应的考试). <br/>
     * @param id
     * @return ResultModel
     */
    ResponseResult queryExams(String id);

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