package com.example.jmgexamsys03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Comparsiontable;
import com.example.jmgexamsys03.entity.Compexamstu;
import com.example.jmgexamsys03.entity.Exam;
import com.example.jmgexamsys03.entity.Student;
import com.example.jmgexamsys03.mapper.ComparsiontableMapper;
import com.example.jmgexamsys03.mapper.ExamMapper;
import com.example.jmgexamsys03.mapper.ExamStuMapper;
import com.example.jmgexamsys03.service.StudentService;
import com.example.jmgexamsys03.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private ComparsiontableMapper comparsiontableMapper;
    @Autowired
    private ExamStuMapper examStuMapper;
    @Autowired
    private ExamMapper examMapper;
    @Override
    public ResponseResult queryExams() {
        long uid = UserThreadLocal.get().getUid();
        QueryWrapper<Comparsiontable> qwt = new QueryWrapper<>();
        qwt.eq("uid",uid);
        // 获取当前学生的sid
        long sid = comparsiontableMapper.selectOne(qwt).getSid();
        QueryWrapper<Compexamstu> qwes = new QueryWrapper<>();
        qwes.eq("sid",sid);
        // 在对照表里面获取每个学生参加的每个考试
        List<Compexamstu> lt = examStuMapper.selectList(qwes);
        List<Exam> retl = new ArrayList<>();
        for(Compexamstu ele :lt){
            Exam examt =  examMapper.selectOne(new QueryWrapper<Exam>().eq("eid",ele.getEid()));
            if(examt!=null){
                retl.add(examt);
            }
        }
        return ResponseResult.okResult(retl);
    }

    @Override
    public ResponseResult displayQuestion(int eId) {
        return null;
    }

    @Override
    public ResponseResult bindIp(Student student) {
        return null;
    }
}
