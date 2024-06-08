package com.example.jmgexamsys03.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jmgexamsys03.entity.Compexamstu;

import org.apache.ibatis.annotations.Mapper;

@TableName("compexamstu")
@Mapper
public interface ExamStuMapper extends BaseMapper<Compexamstu> {
}