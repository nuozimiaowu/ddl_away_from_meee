package com.example.cooperation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("winter_holiday_favorites")
public class Favorite {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("user_id")
    private String userId;
    
    @TableField("article_id")
    private Long articleId;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
}