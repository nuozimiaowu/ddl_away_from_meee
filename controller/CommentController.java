package com.example.cooperation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cooperation.annotation.UserLoginToken;
import com.example.cooperation.common.Result;
import com.example.cooperation.mapper.CommentMapper;
import com.example.cooperation.mapper.CommentMapper2;
import com.example.cooperation.pojo.Article;
import com.example.cooperation.pojo.Comment;
import com.example.cooperation.pojo.Comment2;
import com.example.cooperation.pojo.User;
import com.example.cooperation.service.ArticleService;
import com.example.cooperation.service.CommentService;
import com.example.cooperation.service.CommentService2;
import com.example.cooperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@UserLoginToken
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentMapper2 commentMapper2;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentService2 commentService2;

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    //添加一级评论
    @CrossOrigin
    @PostMapping("/new_main")
    public Result<String> newComment(@RequestBody Comment comment, HttpServletRequest request){
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token error");
        }
        // 设置评论的发布时间为当前时间
        comment.setPublishTime(LocalDateTime.now());

        //  设置评论的人
        comment.setAuthorId(user.getUserId()); // 假设User对象有getUsername()方法

        comment.setAuthorName(user.getUserName());

        // 保存文章到数据库
        commentService.save(comment);

        int articleId = Math.toIntExact(comment.getArticleId());

        articleService.incrementCommentNumber(articleId);

        // 返回成功结果，这里的Result是假设你有一个用于封装响应的Result类
        return Result.success("comment created successfully");
    }


    //添加二级评论
    @CrossOrigin
    @PostMapping("/new_extra")
    public Result<String> newCommentExtra(@RequestBody Comment2 comment, HttpServletRequest request){
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token error");
        }
        // 设置评论的发布时间为当前时间
        comment.setPublishTime(LocalDateTime.now());

        //  设置评论的人
        comment.setAuthorId(user.getUserId()); // 假设User对象有getUsername()方法

        comment.setAuthorName(user.getUserName());

        // 保存文章到数据库
        commentService2.save(comment);

        int articleId = Math.toIntExact(comment.getArticleId());

        articleService.incrementCommentNumber(articleId);
        // 返回成功结果，这里的Result是假设你有一个用于封装响应的Result类
        return Result.success("comment created successfully");
    }

    @CrossOrigin
    @GetMapping("/get_all_main_comment")
    // 获取文章的一级评论
    public List<Comment> getFirstLevelComments(@RequestParam int id) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", id);

        return commentMapper.selectList(queryWrapper);
    }

    // 获取给定评论的二级评论
    @CrossOrigin
    @GetMapping("/get_extra_comment")
    public List<Comment2> getSecondLevelComments(@RequestParam int id) {
        QueryWrapper<Comment2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        return commentMapper2.selectList(queryWrapper);
    }
}
