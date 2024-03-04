package com.example.cooperation.controller;
import com.example.cooperation.common.Result;
import com.example.cooperation.pojo.Article;
import com.example.cooperation.pojo.Favorite;
import com.example.cooperation.pojo.User;
import com.example.cooperation.service.ArticleService;
import com.example.cooperation.service.FavoriteService;
import com.example.cooperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @Autowired
    ArticleService articleService;

    // 收藏文章
    @CrossOrigin
    @PostMapping("add")
    public Result<Integer> favoriteArticle(@RequestBody Favorite favorite, HttpServletRequest request) {
        //对于token做检验：
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token不存在");
        }
        //token检验完成。
        favoriteService.favoriteArticle(user.getUserId(), favorite.getArticleId());

        int id = Math.toIntExact(favorite.getArticleId());
        articleService.incrementFavoriteNumber(id);
        Article byId = articleService.getById(id);
        int favoriteNumber = byId.getFavoriteNumber();

        return Result.success(favoriteNumber);
    }

    // 取消收藏
    @CrossOrigin
    @DeleteMapping("delete")
    public Result<Integer> unfavoriteArticle(@RequestBody Favorite favorite , HttpServletRequest request) {
        //对于token做检验：
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token不存在");
        }
        favoriteService.unfavoriteArticle(user.getUserId(), favorite.getArticleId());

        int id = Math.toIntExact(favorite.getArticleId());
        articleService.decrementFavoriteNumber(id);
        return Result.success(articleService.getById(favorite.getArticleId()).getFavoriteNumber());
    }
}