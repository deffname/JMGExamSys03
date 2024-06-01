package com.example.jmgexamsys03.entity;

import java.util.Arrays;

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
    private String starttime;
    private String endtime;
    private String examname;
    private String exampaper;

    public Exam(String starttime,String endtime,String name,String exampaper){
        this.starttime=starttime;
        this.endtime=endtime;
        this.examname=name;
        this.exampaper=exampaper;
    }
}