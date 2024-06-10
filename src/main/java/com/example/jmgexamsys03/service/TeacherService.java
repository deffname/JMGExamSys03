package com.example.jmgexamsys03.service;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.AddStudentDto;
import com.example.jmgexamsys03.entity.Dto.CheckExamDto;
import com.example.jmgexamsys03.entity.Dto.CreateExamDto;
import com.example.jmgexamsys03.entity.Dto.DeleteStudentDto;

import java.util.List;

public interface TeacherService {
    /**
     * 教师创建新的考试
     * @param createExamDto
     * @return通用响应类型
     */
    ResponseResult CreateExam(CreateExamDto createExamDto);
    
    /**
     * 教师改变考试信息
     * @param changeExamDto
     * @return通用响应类型
     */
//    ResponseResult ChangeExam(ChangeExamDto changeExamDto);
    /**
     * 教师添加考试的学生
     * @param addStudentDto
     * @return通用响应类型
     */
    ResponseResult AddStudent(AddStudentDto addStudentDto);
    /**
     * 教师删除考试的学生
     * @param sekey
     * @return通用响应类型
     */
    ResponseResult DeleteStudent(List<String> sekey);

    /**
     * 教师删除考试
     * @param eidl
     * @return通用响应类型
     */
    ResponseResult DeleteExam(List<Long> eidl);

    /**
     * 教师开启考试
     * @param
     * @return通用响应类型
     */
    ResponseResult StartExam(long eid);
    /**
     * 考试中查看考试详情
     * @param checkExamDto
     * @return考试人数
     */
    ResponseResult CheckExam(CheckExamDto checkExamDto);
    /**
     * 教师结束考试
     * @param
     * @return通用响应类型
     */
    ResponseResult EndExam(long eid);

    /**
     * 教师获取考试列表
     * @param
     * @return通用响应类型
     */

    ResponseResult getExam(long tid);

    /**
     * 教师获取全部学生列表
     * @param
     * @return通用响应类型，包括一个包含学生信息的列表
     */
    ResponseResult getStudent();

    /**
     * 教师获取当前考试的学生列表
     * @param
     * @return通用响应类型，包括一个包含学生信息的列表
     */
    ResponseResult getEStudent(long eid);

    /**
     * 教师获取学生的答案文件所在的位置
     * @param
     * @return通用响应类型
     */
    ResponseResult getAnsL(List<String> sekeyl);

}
