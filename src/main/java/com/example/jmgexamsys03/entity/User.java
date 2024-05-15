package com.example.jmgexamsys03.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private long uid;
    private String username;
    private String fullname;
    private String password;
    private String identity;

    /*
        当User对象被序列化为JSON字符串时，salt属性将不会被包含在生成的JSON中，
        也不会从JSON字符串中解析为User对象的属性。这样可以在序列化和反序列化过程中保护敏感信息的安全性。
     */
    @JsonIgnore
    private String salt = UUID.randomUUID().toString().replaceAll("-","");

    public User(String username,String password, String fullname, String identity) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.identity = identity;
    }
}
