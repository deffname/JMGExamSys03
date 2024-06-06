package com.example.jmgexamsys03.service.impl;


import java.util.Iterator;
import java.util.List;

import com.example.jmgexamsys03.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
import com.example.jmgexamsys03.entity.Dto.AddStudentDto;
import com.example.jmgexamsys03.entity.Dto.ChangeExamDto;
import com.example.jmgexamsys03.entity.Dto.CheckExamDto;
import com.example.jmgexamsys03.entity.Dto.CreateExamDto;
import com.example.jmgexamsys03.entity.Dto.DeleteStudentDto;
import com.example.jmgexamsys03.entity.Dto.UploadExamDto;
import com.example.jmgexamsys03.mapper.AdminMapper;
import com.example.jmgexamsys03.mapper.ComparsiontableMapper;
import com.example.jmgexamsys03.mapper.ExamMapper;
import com.example.jmgexamsys03.mapper.ExamStuMapper;
import com.example.jmgexamsys03.mapper.StudentMapper;
import com.example.jmgexamsys03.mapper.TeacherMapper;
import com.example.jmgexamsys03.mapper.UserMapper;
import com.example.jmgexamsys03.service.TeacherService;
import com.example.jmgexamsys03.utils.UserThreadLocal;

@Service
public class TeacherServiceImpl implements TeacherService{
    private UserMapper userMapper;
    private ComparsiontableMapper comparsiontableMapper;
    private StudentMapper studentMapper;
    private TeacherMapper teacherMapper;
    private AdminMapper adminMapper;
    private ExamMapper examMapper;
    private ExamStuMapper examStuMapper;


    @Autowired
    public TeacherServiceImpl(UserMapper userMapper, ComparsiontableMapper comparsiontableMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, AdminMapper adminMapper,ExamMapper examMapper,ExamStuMapper examStuMapper){
        this.userMapper = userMapper;
        this.comparsiontableMapper = comparsiontableMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.adminMapper = adminMapper;
        this.examMapper=examMapper;
        this.examStuMapper=examStuMapper;
    }
    @Override
    public ResponseResult CreateExam(CreateExamDto createExamDto){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        } 

        Exam exam=new Exam(
            createExamDto.getStarttime(),
            createExamDto.getEndtime(),
            createExamDto.getExamname(),
            createExamDto.getExampaper()
        );
        examMapper.insert(exam);
        return ResponseResult.okResult("创建成功");
    }
    @Override
    public ResponseResult ChangeExam(ChangeExamDto changeExamDto){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("eid",changeExamDto.getEid());
        Exam exam = examMapper.selectOne(examQueryWrapper);
        //判断考试是否存在
        if(exam==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST,"要改变的考试不存在");
        }
        exam.setStarttime(changeExamDto.getStarttime());
        exam.setEndtime(changeExamDto.getEndtime());
        exam.setExampaper(changeExamDto.getExampaper());
        examMapper.update(exam, examQueryWrapper);
        return ResponseResult.okResult("改变成功");
    }
    @Override
    public ResponseResult UploadExam(UploadExamDto uploadExamDto){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("eid",uploadExamDto.getEid());
        Exam exam = examMapper.selectOne(examQueryWrapper);

        if(exam==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST,"要改变的考试不存在");
        }

        exam.setExampaper(uploadExamDto.getExampaper());
        examMapper.update(exam, examQueryWrapper);
        return ResponseResult.okResult("上传成功");
    }
    @Override
    public ResponseResult AddStudent(AddStudentDto addStudentDto){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //判断考试是否存在
            QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
            examQueryWrapper.eq("eid",addStudentDto.getEid());
            Exam exam = examMapper.selectOne(examQueryWrapper);
            if(exam==null){
                return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST,"学生要参加的考试不存在");
            }
        //将学生加入考试
        int count=0;
        while(count<addStudentDto.getSidlist().length){
            Compexamstu compexamstu=new Compexamstu(
            addStudentDto.getSidlist()[count],
            addStudentDto.getEid()
            );
            //判断要添加的是否为学生
            QueryWrapper<User> usQueryWrapper= new QueryWrapper<>();
            usQueryWrapper.eq("uid",compexamstu.getSid()).eq("identity","student");
            User user2=userMapper.selectOne(usQueryWrapper);
            if(user2==null){
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST,"uid"+String.valueOf(compexamstu.getSid())+"此用户不是学生或不存在");
            }
            //判断要添加的是否已经在考试列表中
            QueryWrapper<Compexamstu> compexamQueryWrapper=new QueryWrapper<>();
            compexamQueryWrapper.eq("sid",compexamstu.getSid());
            Compexamstu user3=examStuMapper.selectOne(compexamQueryWrapper);
            if(!(user3==null)){
                return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_SELECTED,"uid"+String.valueOf(compexamstu.getSid())+"此学生已在考试列表中");
            }

            examStuMapper.insert(compexamstu);
            count++;
        }

        return ResponseResult.okResult("添加成功");
    }
    @Override
    public ResponseResult DeleteStudent(DeleteStudentDto deleteStudentDto){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //判断考试是否存在
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("eid",deleteStudentDto.getEid());
        Exam exam = examMapper.selectOne(examQueryWrapper);
        if(exam==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST,"教师要删除的考试不存在");
        }
        //将学生从考试中删除
        int count=0;
        while(count<deleteStudentDto.getSidlist().length){
            QueryWrapper<Compexamstu> compexamstuQueryWrapper = new QueryWrapper<>();
            compexamstuQueryWrapper.in("sid",deleteStudentDto.getSidlist()[count]);
            examStuMapper.delete(compexamstuQueryWrapper);
            count++;
        }
        return ResponseResult.okResult("删除成功");
    }
    @Override
    public ResponseResult StartExam(){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return ResponseResult.okResult("开启成功");
    }
    @Override
    public ResponseResult CheckExam(CheckExamDto checkExamDto){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //判断考试是否存在
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("eid",checkExamDto.getEid());
        Exam exam = examMapper.selectOne(examQueryWrapper);
        if(exam==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST,"要查看的考试不存在");
        }
        //对考试学生进行计数
        QueryWrapper<Compexamstu> compexamstuQueryWrapper = new QueryWrapper<>();
        compexamstuQueryWrapper.in("eid",checkExamDto.getEid());
        List<Compexamstu> compexamstu= examStuMapper.selectList(compexamstuQueryWrapper);
        Iterator<Compexamstu> iter = compexamstu.iterator();
        int stuSum=0;
        while (iter.hasNext()) {
            Compexamstu Compexamstu2 = iter.next();
            stuSum++;
        }
        return ResponseResult.okResult("考试人数为"+stuSum);
    }
    @Override
    public ResponseResult EndExam(){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return ResponseResult.okResult("结束成功");
    }

    public ResponseResult getExam(){
        System.out.println("获取考试服务被调用");
        long uid = UserThreadLocal.get().getUid();
        QueryWrapper<Comparsiontable> qwt = new QueryWrapper<>();
        qwt.eq("uid",uid);
        long tid = comparsiontableMapper.selectOne(qwt).getTid();
        QueryWrapper<Exam> qwe = new QueryWrapper<>();
        qwe.eq("tid",tid);
        List<Exam> exams = examMapper.selectList(qwe);

        return ResponseResult.okResult(exams);

    }
}
