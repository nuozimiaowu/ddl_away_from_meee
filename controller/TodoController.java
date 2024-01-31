package com.example.project4.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.project4.common.Result;
import com.example.project4.pojo.Todo;
import com.example.project4.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoService todoMapper;

    @CrossOrigin
    @PostMapping("add")
    public Result<String> createTodo(@RequestBody Todo todo) {
        // 将收到的数据封装到Todo对象中，然后添加到数据库
        // 验证id、startTime和content不能为空\
        if (todoService.isIdExists(todo.getId())) {
            return Result.error("id已存在");
        }
        if (StringUtils.isBlank(todo.getMyStartTime()) || StringUtils.isBlank(todo.getContent())) {
            return Result.error("id、start_time和content不能为空");
        }
        boolean mysave = todoService.Mysave(todo);
        if (mysave) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    @CrossOrigin
    @PutMapping("/update")
    public Result<String> updateTodoByIdAndContent(@RequestBody Todo todo) {
        Result<String> result = todoService.updateTodoByIdAndContent(todo);

        if (result.getCode() == 1) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    @CrossOrigin
    @PutMapping("/toggleState")
    public Result<String> toggleTodoStateById(@RequestBody Todo todo) {
        Result<String> result = todoService.toggleTodoStateById(todo.getId());
        if (result.getCode() == 1) {
            return Result.success("切换状态成功");
        } else {
            return Result.error("切换状态失败");
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public Result<String> deleteTodoById(@RequestBody Todo todo) {
        Result<String> result = todoService.deleteTodoById(todo.getId());

        if (result.getCode() == 1) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }


    @CrossOrigin
    @GetMapping("showAll")
    public List<Todo> showALL(){
        List<Todo> list = todoService.selectAll();
        return list;
    }


}