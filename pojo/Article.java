package com.example.cooperation.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("winter_holiday_article")
@Data
public class Article {
    @TableId(value = "id")
    private int id;
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
    @TableField(value = "publish_time")
    private LocalDateTime publishTime;
    @TableField(value = "author")
    private  String authorId;
    @TableField(value = "views")
    private int views;
    @TableField(value = "comment_number")
    private int commentNumber;
    @TableField(value = "favorite_number")
    private int favoriteNumber;
    @TableField(value = "author_name")
    private String authorName;
}
