package com.example.jmgexamsys03.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jmgexamsys03.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@TableName("student")
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
