package com.example.jmgexamsys03.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.jmgexamsys03.domain.ResponseResult;
import com.example.jmgexamsys03.domain.enums.AppHttpCodeEnum;
import com.example.jmgexamsys03.entity.Compexamstu;
import com.example.jmgexamsys03.entity.Dto.AddStudentDto;
import com.example.jmgexamsys03.entity.Exam;
import com.example.jmgexamsys03.entity.User;
import com.example.jmgexamsys03.mapper.ExamMapper;
import com.example.jmgexamsys03.mapper.ExamStuMapper;
import com.example.jmgexamsys03.mapper.StudentMapper;
import com.example.jmgexamsys03.mapper.UserMapper;
import com.example.jmgexamsys03.service.TeacherService;
import com.example.jmgexamsys03.utils.UserThreadLocal;
import com.example.jmgexamsys03.utils.fileManage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 专门处理文件上传和下载的类，其中也包括删除文件的方法
 */

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
    @Autowired
    TeacherService teacherService;

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
        String path = "/usr/app/mfile/mpic/";
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

    /**
     * 处理教师上传试卷文件
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
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
        String path = "/usr/app/mfile/epaper/";
        System.out.println("uppic 中 path = " + path);
        saveFile(file,path,fname);

        // 插入文件地址前先看看原先有没有考试文件，如果有的话就删除
        QueryWrapper<Exam> qwe = new QueryWrapper<>();
        qwe.eq("eid",noweid);
        String tmppaper = examMapper.selectOne(qwe).getExampaper();
        if(tmppaper != null){
            System.out.println("上传"+noweid+"考试文件时存在文件，先删除，后添加");
            fileManage.deleteFile(tmppaper);
        }

        UpdateWrapper<Exam> uwe = new UpdateWrapper<>();
        uwe.eq("eid",noweid).set("exampaper","/mfile/epaper/"+fname);

        System.out.println(examMapper.update(null,uwe));
        return ResponseResult.okResult();
    }

    /**
     * 处理学生上传答案文件
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */

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
        String path = "/usr/app/mfile/anspaper/";
        System.out.println("uppic 中 path = " + path);
        saveFile(file,path,fname);

        // 插入文件地址前先看看原先有没有考试文件，如果有的话就删除
        QueryWrapper<Compexamstu> qwes = new QueryWrapper<>();
        qwes.eq("sekey",nsekey);
        String tmppaper = examStuMapper.selectOne(qwes).getAnspaper();
        if(tmppaper != null){
            System.out.println("上传"+nsekey+"考试文件时存在文件，先删除，后添加");
            fileManage.deleteFile(tmppaper);
        }


        UpdateWrapper<Compexamstu> uwe = new UpdateWrapper<>();
        uwe.eq("sekey",nsekey).set("anspaper","/mfile/anspaper/"+fname);

        System.out.println(examStuMapper.update(null,uwe));
        return ResponseResult.okResult();
    }

    @GetMapping("/downloadEPaper")
    public ResponseResult downloadFile(HttpServletResponse response,String filePath) throws IOException {
        System.out.println("下载界面启动");
        // 清空输出流
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(filePath.getBytes("utf-8"), "utf-8"));

        OutputStream os = response.getOutputStream();
        //下载文件的路径
        String downPath = "/usr/app"+filePath;
        //读取目标文件
        File f = new File(downPath);
        //创建输入流
        InputStream is = new FileInputStream(f);
        //做一些业务判断，可以封装到实体类返回具体信息
        if (is == null) {
            System.out.println("文件不存在");
            ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"文件不存在");
        }
        // 利用IOUtils将输入流的内容 复制到输出流
        // org.apache.tomcat.util.http.fileupload.IOUtils
        // 项目搭建是自动集成了这个类 直接使用即可
        IOUtils.copy(is, os);
        os.flush();
        is.close();
        os.close();

        return ResponseResult.okResult();
    }

    @PostMapping("/uploadexcel")
    public ResponseResult uploadExcel(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // 处理第一个表格
            Sheet sheet = workbook.getSheetAt(0);

            List<Long> sids = new ArrayList<>();
            Iterator<Row> rowIterator = sheet.iterator();

            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell sidCell = row.getCell(0); // 假设sid是第一列
                if (sidCell != null) {
                    long sid = (long) sidCell.getNumericCellValue();
                    sids.add(sid);
                }
            }

            long neid = Long.parseLong(request.getHeader("eid"));
            AddStudentDto addStudentDto = new AddStudentDto(sids,neid);
            return teacherService.AddStudent(addStudentDto);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"添加失败");
        }
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
