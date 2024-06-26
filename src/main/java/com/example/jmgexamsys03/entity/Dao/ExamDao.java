package com.example.jmgexamsys03.entity.Dao;



import com.example.jmgexamsys03.entity.Exam;

import java.sql.SQLException;
import java.util.List;


public interface ExamDao {

    /**
     * save:(新建一场考试)
     * @param exam
     * @return
     * @throws SQLException
     */
    int save(Exam exam) throws SQLException;

    /**
     * remove:(移除一场考试)
     * @param id
     * @throws SQLException
     */
    void remove(Integer id) throws SQLException;

    /**
     * modify:(修改考试信息)
     * @param exam
     * @throws SQLException
     */
    void modify(Exam exam) throws SQLException;

    /**
     * queryExamsByTeacher:(查找教师创建的考试)
     * @param teacherId
     * @param key 根据状态查询
     * @return
     * @throws SQLException
     */
    List<Exam> queryExamsByTeacher(String teacherId, String key) throws SQLException;

    /**
     * queryExamsByStudent:(根据考试号查找考试)
     * @param examId
     * @return
     * @throws SQLException
     */
    Exam queryExamsById(int examId) throws SQLException;

    /**
     * setStatus:(设置考试状态)
     * @param id
     * @param status
     * @throws SQLException
     */
    void setState(int id, String status) throws SQLException;

    /**
     * @Description:(根据考试状态查询考试列表). <br/>
     * @param status
     * @return
     * @throws SQLException
     */
    List<Exam> getExamsByState(String status) throws SQLException;



    /**
     * @Description:(清除试题). <br/>
     * @param examId
     * @throws SQLException
     */
    void clearQues(int examId) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Description:(获取自增主键). <br/>
     *
     */
    int getLastInsertID() throws SQLException;

    /**
     * @Description:(获取学生人数). <br/>
     * @param examId
     * @param type
     * @return
     * @throws SQLException
     */
    long getStudentCount(int examId, String type) throws SQLException;

    Exam getLastInsert(String subject, String t_id) throws SQLException;
}