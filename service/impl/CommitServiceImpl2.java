package com.example.cooperation.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperation.mapper.CommentMapper;
import com.example.cooperation.mapper.CommentMapper2;
import com.example.cooperation.mapper.FavoriteMapper;
import com.example.cooperation.pojo.Comment;
import com.example.cooperation.pojo.Comment2;
import com.example.cooperation.service.CommentService;
import com.example.cooperation.service.CommentService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommitServiceImpl2 extends ServiceImpl<CommentMapper2, Comment2> implements CommentService2 {

}
