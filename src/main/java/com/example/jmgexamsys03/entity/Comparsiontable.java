package com.example.jmgexamsys03.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comparsiontable")
public class Comparsiontable {
    private long uid;
    private Long sid;
    private Long tid;
    private Long aid;


}
