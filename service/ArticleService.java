package com.example.cooperation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperation.pojo.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    //增加评论
    void incrementCommentNumber(int articleId);
    //减少评论
    void decrementCommentNumber(int articleId);
    //增加收藏
    void incrementFavoriteNumber(int articleId);
    //减少收藏
    void decrementFavoriteNumber(int articleId);
    //获取某个用户的文章方法
    List<Article> getArticlesByAuthorId(String authorId);

    //以便根据一组 articleId 来获取文章列表
    List<Article> getArticlesByIds(List<Long> articleIds);
}
