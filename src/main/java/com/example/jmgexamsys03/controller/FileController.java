package com.example.jmgexamsys03.controller;

import com.example.jmgexamsys03.domain.ResponseResult;
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

    @PostMapping(value = "/uppic")
    public ResponseEntity<Object> uploadPic(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) throws IOException{

        //得到传过来的文件
        Map<String,MultipartFile> files = multipartHttpServletRequest.getFileMap();
        //得到头像文件
        MultipartFile f = files.get("avatar");


        System.out.println("文件大小为"+f.getSize());

        // 获取服务器的地址目录(开发阶段用的是Tomcat，后续可以是Web服务器)，也就是下面这段代码的前面是不固定的，括号里面是自己任意写的
        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println("uppic 中 path = " + path);
        saveFile(f,path);
        return ResponseEntity.ok(
                new HashMap<String, String>(1) {{
                    put("avatar", path+f.getOriginalFilename());
                }}
        );
    }
    public void saveFile(MultipartFile f,String path) throws IOException{
        File upDir = new File(path);
        if(!upDir.exists()){
            upDir.mkdir();
        }
        File file = new File(path+f.getOriginalFilename());
        f.transferTo(file);
    }
}
