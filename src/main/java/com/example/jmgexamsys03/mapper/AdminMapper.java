package com.example.jmgexamsys03.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jmgexamsys03.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@TableName("teacher")
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
