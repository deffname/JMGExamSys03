package com.example.jmgexamsys03.service;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Teacher;

public interface AdminService {

    /**
     * 添加教师
     * @param teacher
     * @return
     */
    public ResponseResult addTeacher(Teacher teacher);

    /**
     * 根据教师id删除教师
     * @param id
     * @return
     */
    public ResponseResult deleteTeacher(String id);

    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
    public ResponseResult updateTeacher(Teacher teacher);

    /**
     * 查询教师信息
     * @param id
     * @return
     */
    public ResponseResult queryTeacher(String id);

    /**
     * 查询所有教师
     * @return
     */
    public ResponseResult queryAllTeacher();

    /**
     * 重置教师密码
     * @param id
     * @param password
     * @return
     */
    public ResponseResult resetPassword(String id, String password);

}