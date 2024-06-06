package com.example.jmgexamsys03.service;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.AddStudentDto;
import com.example.jmgexamsys03.entity.Dto.ChangeExamDto;
import com.example.jmgexamsys03.entity.Dto.CheckExamDto;
import com.example.jmgexamsys03.entity.Dto.CreateExamDto;
import com.example.jmgexamsys03.entity.Dto.DeleteStudentDto;
import com.example.jmgexamsys03.entity.Dto.UploadExamDto;

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
    ResponseResult ChangeExam(ChangeExamDto changeExamDto);
    /**
     * 教师上传试卷功能
     * @param uploadExamDto
     * @return通用响应类型
     */
    ResponseResult UploadExam(UploadExamDto uploadExamDto);
    /**
     * 教师添加考试的学生
     * @param addStudentDto
     * @return通用响应类型
     */
    ResponseResult AddStudent(AddStudentDto addStudentDto);
    /**
     * 教师删除考试的学生
     * @param deleteStudentDto
     * @return通用响应类型
     */
    ResponseResult DeleteStudent(DeleteStudentDto deleteStudentDto);
    /**
     * 教师开启考试
     * @param
     * @return通用响应类型
     */
    ResponseResult StartExam();
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
    ResponseResult EndExam();





    /**
     * 教师获取考试列表
     * @param
     * @return通用响应类型
     */

    ResponseResult getExam();

    /**
     * 教师获取学生的答案
     * @param registerUserDto
     * @return通用响应类型
     */

}
