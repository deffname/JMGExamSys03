package com.example.jmgexamsys03.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jmgexamsys03.entity.Exam;
import org.apache.ibatis.annotations.Mapper;

@TableName("exam")
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
}
