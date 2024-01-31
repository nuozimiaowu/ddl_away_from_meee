package com.example.project4.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project4.common.Result;
import com.example.project4.mapper.TodoMapper;
import com.example.project4.pojo.Todo;
import com.example.project4.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {

    @Autowired
    TodoMapper todoMapper;
    @Override
    public boolean Mysave(Todo todo) {
        boolean success = save(todo); // This will insert a new record into the database
        return success;
    }

    public boolean isIdExists(int id) {
        QueryWrapper<Todo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = count(queryWrapper);
        return count > 0;
    }

    @Override
    public Result<String> updateTodoByIdAndContent(Todo todo) {
        if (todo.getId() == 0 || StringUtils.isBlank(todo.getMyStartTime()) || StringUtils.isBlank(todo.getContent())) {
            return Result.error("id、start_time和content不能为空");
        }

        // 根据ID查询数据库中的Todo
        Todo existingTodo = getById(todo.getId());

        if (existingTodo == null) {
            return Result.error("找不到ID为" + todo.getId() + "的Todo");
        }

        // 更新内容
        existingTodo.setMyStartTime(todo.getMyStartTime());
        existingTodo.setContent(todo.getContent());
        existingTodo.setMyEndTime(todo.getMyEndTime());
        existingTodo.setContent(todo.getContent());
        existingTodo.setState(todo.getState());

        // 执行更新操作
        boolean success = updateById(existingTodo);
        if (success) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<String> toggleTodoStateById(int id) {
        // 根据ID查询数据库中的Todo
        Todo existingTodo = getById(id);

        if (existingTodo == null) {
            return Result.error("找不到ID为" + id + "的Todo");
        }

        // 切换states值
        existingTodo.setState(existingTodo.getState() == 1 ? 0 : 1);

        // 执行更新操作
        boolean success = updateById(existingTodo);

        if (success) {
            return Result.success("切换状态成功");
        } else {
            return Result.error("切换状态失败");
        }
    }

    @Override
    public Result<String> deleteTodoById(int id) {
        // 根据ID查询数据库中的Todo
        Todo existingTodo = getById(id);

        if (existingTodo == null) {
            return Result.error("找不到ID为" + id + "的Todo");
        }

        // 执行删除操作
        boolean success = removeById(id);

        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public List<Todo> selectAll() {
        return todoMapper.selectList(null);
    }
}

