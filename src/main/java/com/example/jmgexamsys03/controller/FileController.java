package com.example.jmgexamsys03.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.entity.User;
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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
public class FileController {
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir") + "/upload";

    // 保存照片存储时的名字
    private String avatar_name;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/uppic")
    public ResponseEntity<Object> uploadPic(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) throws IOException{

        //得到传过来的文件
        Map<String,MultipartFile> files = multipartHttpServletRequest.getFileMap();
        //得到头像文件
        MultipartFile f = files.get("avatar");
        // System.out.println("文件的原始名称是:" + f.getOriginalFilename()); 估计是前端的组件做了处理，这里的原始名称全是abatar.png
        this.avatar_name = UserThreadLocal.get().getUid()+ "_avatar.png";

        System.out.println("文件大小为"+f.getSize());

//        // 获取服务器的地址目录(开发阶段用的是Tomcat，后续可以是Web服务器)，也就是下面这段代码的前面是不固定的，括号里面是自己任意写的
//        String path = request.getServletContext().getRealPath("/upload/");
//        System.out.println("uppic 中 request.getServletContext() = " + request.getServletContext().getRealPath(""));
        String path = "D:/user_app/mfile/mpic/";
        System.out.println("uppic 中 path = " + path);
        saveFile(f,path);

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
    public void saveFile(MultipartFile f,String path) throws IOException{
        File upDir = new File(path);
        if(!upDir.exists()){
            upDir.mkdir();
        }
        // 将文件存储到对应目录中去
        File file = new File(path+this.avatar_name);
        f.transferTo(file);
    }
}
