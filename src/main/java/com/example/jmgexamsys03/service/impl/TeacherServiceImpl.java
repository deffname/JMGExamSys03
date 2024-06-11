package com.example.jmgexamsys03.service.impl;


import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.example.jmgexamsys03.entity.*;

import com.example.jmgexamsys03.entity.Dto.*;
import com.example.jmgexamsys03.utils.fileManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
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
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR,"当前身份有问题");
        }
        long uid = user.getUid();
        QueryWrapper<Comparsiontable> qwt = new QueryWrapper<>();
        qwt.eq("uid",uid);
        long tid = comparsiontableMapper.selectOne(qwt).getTid();

        System.out.println("创建考试时传送来的信息为"+ createExamDto);
        // 将字符串转换为时间
        // 先定义时间转换的格式
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stime = createExamDto.getEdate() +" "+ createExamDto.getStarttime()+":00";
        String etime = createExamDto.getEdate() +" "+ createExamDto.getEndtime()+":00";
        Date st = null;
        Date et = null;
        try {
            st = formatter.parse(stime);
            et = formatter.parse(etime);


            // 使用Calendar给st和et加一天，因为前端那个组件的时区问题，导致选择的时间比传送回来的时间提前一天
            Calendar calendarSt = Calendar.getInstance();
            calendarSt.setTime(st);
            calendarSt.add(Calendar.DAY_OF_MONTH, 1); // 给开始时间加一天
            st = calendarSt.getTime();

            Calendar calendarEt = Calendar.getInstance();
            calendarEt.setTime(et);
            calendarEt.add(Calendar.DAY_OF_MONTH, 1); // 给结束时间加一天
            et = calendarEt.getTime();

        } catch (Exception e){
            System.out.println("时间转换错误");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Exam exam=new Exam(
            st,
            et,
            createExamDto.getExamname(),
                tid
        );
        examMapper.insert(exam);
        return ResponseResult.okResult(exam.getEid());
    }
 
//    public ResponseResult ChangeExam(ChangeExamDto changeExamDto){
//        User user = UserThreadLocal.get();
//        if (!user.getIdentity().equals("teacher")){
//            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
//        }
//
//        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
//        examQueryWrapper.eq("eid",changeExamDto.getEid());
//        Exam exam = examMapper.selectOne(examQueryWrapper);
//        //判断考试是否存在
//        if(exam==null){
//            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST);
//        }
////        exam.setStarttime(changeExamDto.getStarttime());
////        exam.setEndtime(changeExamDto.getEndtime());
////        exam.setExampaper(changeExamDto.getExampaper());
////        examMapper.update(exam, examQueryWrapper);
//        return ResponseResult.okResult("改变成功");
//    }

//    public ResponseResult UploadExam(UploadExamDto uploadExamDto){
//        User user = UserThreadLocal.get();
//        if (!user.getIdentity().equals("teacher")){
//            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
//        }
//
//        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
//        examQueryWrapper.eq("eid",uploadExamDto.getEid());
//        Exam exam = examMapper.selectOne(examQueryWrapper);
//
//        if(exam==null){
//            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST,"要改变的考试不存在");
//        }
//
//        exam.setExampaper(uploadExamDto.getExampaper());
//        examMapper.update(exam, examQueryWrapper);
//        return ResponseResult.okResult("上传成功");
//    }
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
        List<Long> sidlist = addStudentDto.getSidlist();
        long eidt = addStudentDto.getEid();
        for(long sidt:sidlist){
            String tmp = eidt +"_" + sidt;
            Compexamstu compexamstu=new Compexamstu(sidt, eidt, tmp);
            //判断要添加的是否为学生

            //判断要添加的是否已经在考试列表中
            try{
                examStuMapper.insert(compexamstu);
            }catch (Exception e){
                if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                    // 这是由于违反了唯一约束（比如主键冲突）而抛出的异常
                    System.out.println("学号为"+sidt+"的学生已经加入过了");
                } else {
                    // 其他类型的异常，可能需要不同的处理逻辑
                    e.printStackTrace();
                    return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"添加学生信息时出现错误");
                }
            }
        }
        return ResponseResult.okResult("添加成功");
    }
    @Override
    public ResponseResult DeleteStudent(List<String> sekeyl){
        System.out.println("传递进来的sekeyl"+sekeyl);
        for(String mse:sekeyl){
            System.out.println("now mse = "+mse);
            String tmppaper = examStuMapper.selectById(mse).getAnspaper();
            if(tmppaper!=null){
                fileManage.deleteFile(tmppaper);
            }
            examStuMapper.deleteById(mse);
        }
        return ResponseResult.okResult("删除成功");
    }
    @Override
    public ResponseResult StartExam(long eid){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        Exam examtmp = examMapper.selectById(eid);
        if(examtmp == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"考试不存在");
        }
        if(!examtmp.getState().equals("notstart")){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"考试已经开始或结束，无法开始考试");
        }

        // 开始计算时间差值
        LocalDateTime starttime = examtmp.getStarttime().toInstant().
                atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 计算时间差
        Duration duration = Duration.between(now,starttime);
        // 将Duration转换为分钟
        long minutesBetween = duration.toMinutes();
        long shouldt = examtmp.getPretime();

        System.out.println("数据库时间"+examtmp.getStarttime()+"&&改变后的数据库时间"+starttime);
        System.out.println("本地时间"+now+"&&时间差值"+minutesBetween);

        if(minutesBetween>shouldt){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,
                    "距离可以开启考试还有"+(minutesBetween-shouldt)+"分钟");
        }

        examtmp.setState("starting");
        examMapper.update(examtmp,new QueryWrapper<Exam>().eq("eid",eid));
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
    public ResponseResult EndExam(long eid){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        Exam examtmp = examMapper.selectById(eid);
        if(examtmp == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"考试不存在");
        }
        if(!examtmp.getState().equals("starting")){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"考试尚未开始或者已经结束，无法结束考试");
        }

        examtmp.setState("started");
        examMapper.update(examtmp,new QueryWrapper<Exam>().eq("eid",eid));
        return ResponseResult.okResult("结束成功");
    }

    public ResponseResult getExam(long tid){
//        System.out.println("获取考试服务被调用");

        QueryWrapper<Exam> qwe = new QueryWrapper<>();
        qwe.eq("tid",tid);
        List<Exam> exams = examMapper.selectList(qwe);

        return ResponseResult.okResult(exams);

    }

    public ResponseResult getStudent(){
        User user = UserThreadLocal.get();
        if (!user.getIdentity().equals("teacher")){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        QueryWrapper<Student> qws = new QueryWrapper<>();
        // 获取用户中的所有学生
        List<Student> stuList =  studentMapper.selectList(qws);
        return ResponseResult.okResult(stuList);
    }

    public ResponseResult DeleteExam(List<Long> eidl){
        for(Long eidt:eidl){
            Exam examtmp = examMapper.selectById(eidt);
            if(examtmp.getExampaper()!=null){
                // 删除对应的考试文件
                fileManage.deleteFile(examtmp.getExampaper());
            }
            // 删除对应考试下的所有学生的答案文件
            QueryWrapper<Compexamstu> qwest = new QueryWrapper<>();
            qwest.eq("eid",eidt);
            List<Compexamstu> estmpl =  examStuMapper.selectList(qwest);

            // 遍历所有考试下的学生的答案，如果提交的有对应文件就删除
            for(Compexamstu est:estmpl){
                if(est.getAnspaper()!=null){
                    // 删除对应的考试文件
                    fileManage.deleteFile(est.getAnspaper());
                }
            }

            examMapper.deleteById(eidt);
        }
        return ResponseResult.okResult("删除对应考试成功");
    }

    public ResponseResult getEStudent(long eid){
        QueryWrapper<Compexamstu> qwces = new QueryWrapper<>();
        qwces.eq("eid",eid);
        List<Compexamstu> myces = examStuMapper.selectList(qwces);
        List<getEStuResDto> retl = new ArrayList<>();
        for(Compexamstu ces:myces){
            Student st = studentMapper.selectById(ces.getSid());
            if(st==null){
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
            }
            retl.add(new getEStuResDto(
                    ces.getSid(),
                    st.getSname(),
                    ces.getAnspaper()
            ));

        }
        return ResponseResult.okResult(retl);
    }

    @Override
    public ResponseResult getAnsL(List<String> sekeyl) {
        Map<Long,String> ret = new HashMap<>();
        for (String nsekey:sekeyl){
            Compexamstu tmp =  examStuMapper.selectById(nsekey);
            if(tmp == null){
                continue;
            }
            ret.put(tmp.getSid(),tmp.getAnspaper());
        }

//        examStuMapper.selectById(msekey).getAnspaper();
        return ResponseResult.okResult(ret);
    }
}
