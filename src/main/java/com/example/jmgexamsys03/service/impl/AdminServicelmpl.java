package com.example.jmgexamsys03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
import com.example.jmgexamsys03.entity.Dto.AUserInfoResDto;
import com.example.jmgexamsys03.entity.Dto.setPreETimeDto;
import com.example.jmgexamsys03.entity.Exam;
import com.example.jmgexamsys03.entity.User;
import com.example.jmgexamsys03.mapper.ExamMapper;
import com.example.jmgexamsys03.mapper.UserMapper;
import com.example.jmgexamsys03.service.AdminService;
import com.example.jmgexamsys03.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServicelmpl implements AdminService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExamMapper examMapper;
    @Override
    public ResponseResult queryAllUser() {
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("admin")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        QueryWrapper<User> qwu = new QueryWrapper<>();
        List<User> ul =  userMapper.selectList(qwu);
        List<AUserInfoResDto> rel = new ArrayList<>();
        for(User u:ul){
            rel.add(new AUserInfoResDto(u.getUid(),u.getFullname(),u.getIdentity()));
        }
        return ResponseResult.okResult(rel);
    }

    @Override
    public ResponseResult resetPassword(long uid, String password) {

        User nuser = UserThreadLocal.get();
        if (!nuser.getIdentity().equals("admin")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        User user = userMapper.selectById(uid);
        String md5Pwd = DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes());

        UpdateWrapper<User> uwu = new UpdateWrapper<>();
        uwu.eq("uid",uid).set("password",md5Pwd);
        if(userMapper.update(null,uwu)==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST,"没有找到对应用户");
        }


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllExam() {
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("admin")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        QueryWrapper<Exam> qwu = new QueryWrapper<>();
        List<Exam> retl =  examMapper.selectList(qwu);

        return ResponseResult.okResult(retl);
    }

    @Override
    public ResponseResult setETime(setPreETimeDto setPreETimeDto) {
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("admin")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        UpdateWrapper<Exam> qwe = new UpdateWrapper<>();
        qwe.eq("eid",setPreETimeDto.getEid()).set("pretime",setPreETimeDto.getUtime());
        if(examMapper.update(null,qwe)==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST,"没有找到对应考试");
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult setEState(long eid, String state) {
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("admin")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        UpdateWrapper<Exam> qwe = new UpdateWrapper<>();
        qwe.eq("eid",eid).set("state",state);
        if(examMapper.update(null,qwe)==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST,"没有找到对应考试");
        }

        return ResponseResult.okResult();
    }
}
