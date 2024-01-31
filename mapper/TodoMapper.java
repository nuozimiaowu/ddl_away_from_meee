package com.example.project4.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.project4.pojo.Todo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
}
