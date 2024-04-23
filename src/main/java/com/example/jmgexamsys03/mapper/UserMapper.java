package com.example.jmgexamsys03.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jmgexamsys03.entity.User;
import org.apache.ibatis.annotations.Mapper;

// @Mapper说明下面的接口是一个mapper组件
@TableName("user")
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
