package com.example.jmgexamsys03.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
import com.example.jmgexamsys03.entity.*;
import com.example.jmgexamsys03.entity.Dto.LoginDto;
import com.example.jmgexamsys03.entity.Dto.LoginUserResponseDto;
import com.example.jmgexamsys03.entity.Dto.RegisterUserDto;
import com.example.jmgexamsys03.entity.Dto.infoResponseDto;
import com.example.jmgexamsys03.mapper.*;
import com.example.jmgexamsys03.service.UserService;

import com.example.jmgexamsys03.utils.JwtUtils;
import com.example.jmgexamsys03.utils.RedisCache;
import com.example.jmgexamsys03.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private ComparsiontableMapper comparsiontableMapper;
    private StudentMapper studentMapper;
    private TeacherMapper teacherMapper;
    private AdminMapper adminMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, ComparsiontableMapper comparsiontableMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, AdminMapper adminMapper) {
        this.userMapper = userMapper;
        this.comparsiontableMapper = comparsiontableMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.adminMapper = adminMapper;
    }

    /**
     *
     * @param registerUserDto
     * @return
     * 处理注册相关事务，这里只是一个用于测试的实现，需要补充其余东西
     */
    @Override
    public ResponseResult registerUser(RegisterUserDto registerUserDto) {
        User user = new User(
                registerUserDto.getUsername(),
                registerUserDto.getPassword(),
                registerUserDto.getFullname(),
                registerUserDto.getIdentity()
        );
        String pwd = registerUserDto.getPassword();
        String salt = user.getSalt();
        String md5Pwd =DigestUtils.md5DigestAsHex((pwd+salt).getBytes());
        user.setPassword(md5Pwd);

        // 判断用户名称是否已经存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("username",user.getUsername());
        List<User> user1 = userMapper.selectList(userQueryWrapper);
        if(!user1.isEmpty()){
            System.out.println("注册的用户名已存在");
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR,"用户名已存在");
        }

        System.out.println("身份是" + user.getIdentity());
        userMapper.insert(user);

        // 用equals是因为对stringl这种引用类型数据，如果用==比较的是存储的内容地址
        if(user.getIdentity().equals("student")){
            Student stu = new Student();
            // 把学生的真实姓名放到学生表格里面
            stu.setSname(user.getFullname());
            studentMapper.insert(stu);
            Long tmp1 = null;
//            System.out.println("Sid = "+stu.getSid());
//            Long test =new Long(3);
            comparsiontableMapper.insert(new Comparsiontable(user.getUid(),stu.getSid(),tmp1,tmp1));
        } else if (user.getIdentity().equals("teacher")) {
            Teacher tea = new Teacher();
            teacherMapper.insert(tea);
            Long tmp1 = null;
            comparsiontableMapper.insert(new Comparsiontable(user.getUid(),tmp1,tea.getTid(),tmp1));
        }else {
            Admin admin = new Admin();
            adminMapper.insert(admin);
            Long tmp1 = null;
            comparsiontableMapper.insert(new Comparsiontable(user.getUid(),tmp1,tmp1,admin.getAid()));
        }

        return ResponseResult.okResult("注册成功");
    }

    @Override
    public ResponseResult loginUser(LoginDto loginDto) {
        System.out.println("登录信息为" + loginDto);
        String psw = loginDto.getPassword();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",loginDto.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);

        if(user == null){
            // 用户不存在
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR,"不存在此用户");
        }

        String md5psw = DigestUtils.md5DigestAsHex((psw+user.getSalt()).getBytes());
        if(!md5psw.equals(user.getPassword())){
            // 密码验证没有成功
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR,"密码错误");
        }
        System.out.println("登录验证成功");
        // 存储token
        String token = JwtUtils.createToken(user.getUid());//创建一个token
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("TOKEN_"+token);
        //将token插入redis,1天后过期
        redisCache.setCacheObject("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        long rid = 0;
        String nrole = user.getIdentity();
        if (nrole.equals("student")) {
            rid = comparsiontableMapper.selectById(user.getUid()).getSid();
        } else if (nrole.equals("teacher")) {
            rid = comparsiontableMapper.selectById(user.getUid()).getTid();
        } else if (nrole.equals("admin")) {
            rid = comparsiontableMapper.selectById(user.getUid()).getAid();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"账户身份不存在");
        }
        // 返回对应用户的token和身份
        return ResponseResult.okResult(new LoginUserResponseDto(token,nrole,rid));
    }

    /**
     * 前端切换界面时返回信息
     * @param
     * @return 包括用户名，用户身份，用户头像
     */

    public ResponseResult getinfoUser(){
        User tmp = UserThreadLocal.get();
        if(tmp == null){
            return new ResponseResult<>().error(AppHttpCodeEnum.LOGIN_ERROR.getCode(),"请重新登录");
        }

        long rid = 0;
        String nrole = tmp.getIdentity();
        if (nrole.equals("student")) {
            rid = comparsiontableMapper.selectById(tmp.getUid()).getSid();
        } else if (nrole.equals("teacher")) {
            rid = comparsiontableMapper.selectById(tmp.getUid()).getTid();
        } else if (nrole.equals("admin")) {
            rid = comparsiontableMapper.selectById(tmp.getUid()).getAid();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"账户身份不存在");
        }

        return ResponseResult.okResult(new infoResponseDto(tmp.getUsername(),tmp.getIdentity(),tmp.getAvatar(),rid));
    }

    /**
     * 验证token的逻辑
     * @param token
     * @return
     */
    @Override
    public User checkToken(String token) {
//        System.out.println("check token:"+token);
        if(StringUtils.isEmpty(token)){
            return null;
        }
        Map<String,Object> map = JwtUtils.checkToken(token);
        if(map==null){
            return null;
        }
        String userJson =  redisCache.getCacheObject("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }
}
