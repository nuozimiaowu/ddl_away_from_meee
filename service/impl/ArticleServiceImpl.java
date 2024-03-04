package com.example.cooperation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperation.mapper.ArticleMapper;
import com.example.cooperation.pojo.Article;
import com.example.cooperation.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    // 增加评论数
    @Transactional
    public void incrementCommentNumber(int articleId) {
        this.baseMapper.incrementCommentNumber(articleId);
    }

    // 减少评论数
    @Transactional
    public void decrementCommentNumber(int articleId) {
        this.baseMapper.decrementCommentNumber(articleId);
    }

    // 增加收藏数
    @Transactional
    public void incrementFavoriteNumber(int articleId) {
        this.baseMapper.incrementFavoriteNumber(articleId);
    }

    // 减少收藏数
    @Transactional
    public void decrementFavoriteNumber(int articleId) {
        this.baseMapper.decrementFavoriteNumber(articleId);
    }

    //查询用户的文章
    @Override
    public List<Article> getArticlesByAuthorId(String authorId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author", authorId);
        return list(queryWrapper);
    }

    @Override
    public List<Article> getArticlesByIds(List<Long> articleIds) {
        if (articleIds == null || articleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return listByIds(articleIds);
    }
}
