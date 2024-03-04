package com.example.cooperation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperation.pojo.Favorite;

import java.util.List;

public interface FavoriteService extends IService<Favorite> {
    void favoriteArticle(String userId, Long articleId);

    void unfavoriteArticle(String userId, Long articleId);

    List<Favorite> getFavoritesByUserId(String userId);
}
