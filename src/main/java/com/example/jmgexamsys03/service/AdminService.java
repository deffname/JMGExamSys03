package com.example.jmgexamsys03.service;


import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Dto.setPreETimeDto;
import com.example.jmgexamsys03.entity.Teacher;

public interface AdminService {


    /**
     * 查询所有教师
     * @return
     */
    public ResponseResult queryAllUser();

    /**
     * 重置用户密码
     * @param uid
     * @param password
     * @return
     */
    public ResponseResult resetPassword(long uid, String password);

    /**
     * 获取所有考试
     * @return
     */
    public ResponseResult getAllExam();

    /**
     * 更新开启考试距离最长时间
     * @param setPreETimeDto
     * @return
     */
    public ResponseResult setETime(setPreETimeDto setPreETimeDto);

    public ResponseResult setEState(long eid,String state);

}