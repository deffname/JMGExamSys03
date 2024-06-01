package com.example.jmgexamsys03.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("compexamstu")
public class Compexamstu {
    private long  sid;
    private long  eid;
    private String anspaper;
    public Compexamstu(long sid,long eid){
        this.sid=sid;
        this.eid=eid;
    }
}
