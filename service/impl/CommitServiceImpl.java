package com.example.cooperation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperation.mapper.CommentMapper;
import com.example.cooperation.pojo.Comment;
import com.example.cooperation.service.CommentService;
import com.example.cooperation.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CommitServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
