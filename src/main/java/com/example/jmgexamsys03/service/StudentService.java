package com.example.jmgexamsys03.service;

public interface StudentService {

    /**
     * login:(学生登录). <br/>
     * @param student
     * @return ResultModel	返回数据模型
     */
    ResultModel login(Student student);

    /**
     * queryExams:(根据学生id查看所对应的考试). <br/>
     * @param id
     * @return ResultModel
     */
    ResultModel queryExams(String id);

    /**
     * displayQuestion:(根据考试的id来获取试卷信息). <br/>
     * @param eId
     * @return ResultModel
     */
    ResultModel displayQuestion(int eId);

    /**
     * @Description:(保存考生答案). <br/>
     * @param examId
     * @param studentId
     * @param ques
     * @return
     * @throws SQLException
     */
    ResultModel saveAsnwers(int examId, String studentId, List<Question> ques) throws SQLException;

    /**
     * bindIp(开始考试时绑定学生Ip)
     * @param student
     * @return
     */
    ResultModel bindIp(Student student);
}