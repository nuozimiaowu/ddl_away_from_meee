package com.example.cooperation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperation.mapper.FavoriteMapper;
import com.example.cooperation.pojo.Favorite;
import com.example.cooperation.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {
    @Autowired
    FavoriteMapper favoriteMapper;

    // 收藏文章
    public void favoriteArticle(String userId, Long articleId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setArticleId(articleId);
        favorite.setCreatedAt(LocalDateTime.now());
        favoriteMapper.insert(favorite);
    }

    // 取消收藏
    public void unfavoriteArticle(String userId, Long articleId) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("article_id", articleId);
        favoriteMapper.delete(queryWrapper);
    }

    // 检查是否已收藏
    public boolean isFavorited(String userId, Long articleId) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("article_id", articleId);
        return favoriteMapper.selectCount(queryWrapper) > 0;
    }

    //获取一个用户的所有收藏记录，需要传入用户的id，输出所有的用户的id
    @Override
    public List<Favorite> getFavoritesByUserId(String userId) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }
}
