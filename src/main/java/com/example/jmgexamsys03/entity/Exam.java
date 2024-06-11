package com.example.jmgexamsys03.entity;

import java.util.Arrays;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("exam")
public class Exam {
    @TableId(type = IdType.AUTO)
    private long eid;
    private Date starttime;
    private Date endtime;
    private String examname;
    private String exampaper;
    // 开创这个课程的教师的id
    private long tid;
    // notsatrt     starting    started
    private String state = "notstart";
    private long pretime = 15;

    public Exam(Date starttime,Date endtime,String name,long tid){
        this.starttime=starttime;
        this.endtime=endtime;
        this.examname=name;
        this.tid = tid;
    }
}