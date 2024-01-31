package com.example.project4.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("todolist")
@Data
public class Todo {
    @TableId(value = "id")
    private int id;
    @TableField(value = "start_time")
    private String myStartTime;
    @TableField(value = "end_time")
    private String myEndTime;
    @TableField(value = "content")
    private String content;
    @TableField(value = "state")
    private int state;
}
