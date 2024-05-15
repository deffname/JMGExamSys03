package com.example.jmgexamsys03.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jmgexamsys03.entity.Comparsiontable;
import org.apache.ibatis.annotations.Mapper;

@TableName("Comparsiontable")
@Mapper
public interface ComparsiontableMapper extends BaseMapper<Comparsiontable> {
}
