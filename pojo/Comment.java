package com.example.cooperation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@TableName("winter_hoiliday_comment_main")
@Data
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "content")
    private String content; // 评论内容

    @TableField(value = "publish_time")
    private LocalDateTime publishTime; // 发布时间

    @TableField(value = "author_id")
    private String authorId; // 评论用户ID

    @TableField(value = "parent_id")
    private Long parentId; // 父评论ID，对于一级评论，这个字段可以为null

    @TableField(value = "article_id")
    private Long articleId; // 关联的文章ID

    @TableField(value = "author_name")
    private String authorName; // 关联的文章ID
}
