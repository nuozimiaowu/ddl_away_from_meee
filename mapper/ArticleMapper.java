package com.example.cooperation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cooperation.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleMapper  extends BaseMapper<Article> {

    // 自定义增加评论数的方法
    @Update("UPDATE winter_holiday_article SET comment_number = comment_number + 1 WHERE id = #{articleId}")
    void incrementCommentNumber(@Param("articleId") int articleId);

    // 自定义减少评论数的方法
    @Update("UPDATE winter_holiday_article SET comment_number = comment_number - 1 WHERE id = #{articleId} AND comment_number > 0")
    void decrementCommentNumber(@Param("articleId") int articleId);

    // 自定义增加收藏数的方法
    @Update("UPDATE winter_holiday_article SET favorite_number = favorite_number + 1 WHERE id = #{articleId}")
    void incrementFavoriteNumber(@Param("articleId") int articleId);

    // 自定义减少收藏数的方法
    @Update("UPDATE winter_holiday_article SET favorite_number = favorite_number - 1 WHERE id = #{articleId} AND favorite_number > 0")
    void decrementFavoriteNumber(@Param("articleId") int articleId);
}
