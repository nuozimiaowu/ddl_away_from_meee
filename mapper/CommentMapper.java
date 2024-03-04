package com.example.cooperation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cooperation.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    // 在这里定义需要的数据库操作，例如插入评论、查询文章下的所有评论等
}