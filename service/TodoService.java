package com.example.project4.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project4.common.Result;
import com.example.project4.pojo.Todo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public interface TodoService extends IService<Todo> {

    boolean Mysave(Todo todo);

    boolean isIdExists(int id);

    Result<String> updateTodoByIdAndContent(Todo todo);

    public Result<String> toggleTodoStateById(int id);

    Result<String> deleteTodoById(int id);

    List<Todo> selectAll();
}
