package com.example.jmgexamsys03.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.Compexamstu;
import com.example.jmgexamsys03.entity.Exam;
import com.example.jmgexamsys03.entity.User;
import com.example.jmgexamsys03.mapper.ExamMapper;
import com.example.jmgexamsys03.mapper.ExamStuMapper;
import com.example.jmgexamsys03.mapper.StudentMapper;
import com.example.jmgexamsys03.mapper.UserMapper;
import com.example.jmgexamsys03.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;


@RestController
@CrossOrigin
public class FileController {
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir") + "/upload";

    // 保存照片存储时的名字
    private String avatar_name;
    // 用于获取当前时间
    private SimpleDateFormat nowtime;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamStuMapper examStuMapper;

    @PostMapping(value = "/uppic")
    public ResponseEntity<Object> uploadPic(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) throws IOException{

        //得到传过来的文件
        Map<String,MultipartFile> files = multipartHttpServletRequest.getFileMap();
        //得到头像文件
        MultipartFile f = files.get("avatar");
        // System.out.println("文件的原始名称是:" + f.getOriginalFilename()); 估计是前端的组件做了处理，这里的原始名称全是abatar.png

        //时间格式化格式
        nowtime =new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并作为时间戳给文件夹命名
        String timeStamp1 = nowtime.format(new Date());
        this.avatar_name = UserThreadLocal.get().getUid() + timeStamp1+ ".png";

        System.out.println("文件大小为"+f.getSize());

//        // 获取服务器的地址目录(开发阶段用的是Tomcat，后续可以是Web服务器)，也就是下面这段代码的前面是不固定的，括号里面是自己任意写的
//        String path = request.getServletContext().getRealPath("/upload/");
//        System.out.println("uppic 中 request.getServletContext() = " + request.getServletContext().getRealPath(""));
        String path = "D:/user_app/mfile/mpic/";
        System.out.println("uppic 中 path = " + path);
        saveFile(f,path,avatar_name);

        // 将头像地址放到数据库里面去
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        System.out.println("此时获得的对象是"+UserThreadLocal.get());
        updateWrapper.eq("uid", UserThreadLocal.get().getUid()).set("avatar","/mfile/mpic/"+this.avatar_name);
        userMapper.update(null,updateWrapper);

        UserThreadLocal.get().setAvatar("/mfile/mpic/"+this.avatar_name);
//        QueryWrapper<User> qw = new QueryWrapper<>();
//
//        System.out.println("更改过后的");

        return ResponseEntity.ok(
                new HashMap<String, String>(1) {{
                    put("avatar", "/mfile/mpic/"+avatar_name);
                }}
        );
    }

    @PostMapping("upefile")
    public ResponseResult uploadEPaper(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws IOException{
        String fname = file.getOriginalFilename();

        // 获取当前考试的id号码
        long noweid = Long.parseLong(request.getHeader("eid"));
        nowtime =new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并作为时间戳给文件夹命名
        String timeStamp1 = nowtime.format(new Date());

        fname = noweid+"_"+timeStamp1+fname;
        System.out.println("文件名称为"+fname);
        String path = "D:/user_app/mfile/epaper/";
        System.out.println("uppic 中 path = " + path);
        saveFile(file,path,fname);

        UpdateWrapper<Exam> uwe = new UpdateWrapper<>();
        uwe.eq("eid",noweid).set("exampaper","/mfile/epaper/"+fname);

        System.out.println(examMapper.update(null,uwe));
        return ResponseResult.okResult();
    }

    @PostMapping("upAnsfile")
    public ResponseResult uploadAnsPaper(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws IOException{
        String fname = file.getOriginalFilename();

        // 获取当前考试的id号码
        String nsekey = request.getHeader("sekey");
        nowtime =new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并作为时间戳给文件夹命名
        String timeStamp1 = nowtime.format(new Date());

        fname = nsekey+"_"+timeStamp1+fname;
        System.out.println("文件名称为"+fname);
        String path = "D:/user_app/mfile/anspaper/";
        System.out.println("uppic 中 path = " + path);
        saveFile(file,path,fname);

        UpdateWrapper<Compexamstu> uwe = new UpdateWrapper<>();
        uwe.eq("sekey",nsekey).set("anspaper","/mfile/epaper/"+fname);

        System.out.println(examStuMapper.update(null,uwe));
        return ResponseResult.okResult();
    }

    public void saveFile(MultipartFile f,String path,String filename) throws IOException{
        File upDir = new File(path);
        if(!upDir.exists()){
            upDir.mkdir();
        }
        // 将文件存储到对应目录中去
        File file = new File(path+filename);
        f.transferTo(file);
    }
}
