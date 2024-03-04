package com.example.cooperation.controller;
import com.alibaba.fastjson.JSONObject;
import com.example.cooperation.annotation.UserLoginToken;
import com.example.cooperation.common.Result;
import com.example.cooperation.pojo.Article;
import com.example.cooperation.pojo.Favorite;
import com.example.cooperation.pojo.User;
import com.example.cooperation.service.ArticleService;
import com.example.cooperation.service.FavoriteService;
import com.example.cooperation.service.UserService;
import com.example.cooperation.service.impl.TokenService;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FavoriteService favoriteService;



    //首先，我们要进行用户的注册
    @PostMapping("register")
    @CrossOrigin
    public Result save(@RequestBody User user){
        // 检查用户名是否存在
        if (userService.existsByUserName(user.getUserName())) {
            return Result.error("用户名已存在");
        }
        // 如果用户名不存在，则继续注册流程
        boolean save = userService.save(user);
        if (!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    //然后是用户的登录；
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin
    public Result<Object> login(@RequestBody User user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();

        User userForBase = userService.findByUsername(user);

        if (userForBase == null || !userForBase.getUserPassword().equals(user.getUserPassword())) {
            return Result.error("LOGIN FAILURE");
        } else {
            String token = tokenService.getToken(userForBase); // 生成包含用户ID的JWT
            jsonObject.put("token", token);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);

            return Result.success(jsonObject);
        }
    }

    @UserLoginToken
    @PutMapping
    @CrossOrigin
    public Result<String> update(@RequestBody User user){
        userService.update(user);
        return Result.success("修改成功");
    }

    @UserLoginToken
    @GetMapping("all")
    @CrossOrigin
    public Result<User> selectAll(){
        return userService.selectAll();
    }


    @GetMapping("current_user")
    @CrossOrigin
    public Result<User> getCurrentUserInformation(HttpServletRequest request){
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token error");
        }
        return Result.success(user);
    }
    //设置个人的简介

    @PostMapping("introduction")
    @CrossOrigin
    public Result<String> setIntroduction(@RequestBody User user,HttpServletRequest request){
        User user2 = userService.userLoginNow(request);
        if (user2 == null){
            return Result.error("token error");
        }
        user.setUserId(user2.getUserId());
        userService.update(user);
        return Result.success("个人简介修改成功");
    }


    @GetMapping("my_article")
    @CrossOrigin
    public Result<List<Article>> getMyArticle(HttpServletRequest request){
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token error");
        }
        List<Article> articles = articleService.getArticlesByAuthorId(user.getUserId());
        if (articles.isEmpty()) {
            return Result.error("No articles found for the user");
        }
        return Result.success(articles);
    }

    @GetMapping("my_favorites")
    @CrossOrigin
    public Result<List<Article>> getMyFavorites(HttpServletRequest request) {
        User user = userService.userLoginNow(request);
        if (user == null) {
            return Result.error("Token error");
        }
        //得到有关的用户关注列表
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(user.getUserId());
        //螺旋流式编程！从关注列表里找到相关的文章id集合
        List<Long> articleIds = favorites.stream().map(Favorite::getArticleId).collect(Collectors.toList());
        //根据文章id集合得到
        List<Article> articles = articleService.getArticlesByIds(articleIds);
        return Result.success(articles);
    }

    @PostMapping("update_username")
    @CrossOrigin
    public Result<String> updateUserName(@RequestBody User user,HttpServletRequest request){
        User user2 = userService.userLoginNow(request);
        if (user2 == null) {
            return Result.error("Token error");
        }
        user.setUserId(user2.getUserId());
        userService.update(user);
        return Result.success("更新成功");
    }

    @CrossOrigin
    @PostMapping("/change_password")
    public Result<String> changePassword(HttpServletRequest request,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        User user2 = userService.userLoginNow(request);
        if (user2 == null) {
            return Result.error("Token error");
        }
        boolean success = userService.changePassword(user2.getUserId(), oldPassword, newPassword);
        if (success) {
            return Result.success("Password changed successfully.");
        } else {
            return Result.error("Failed to change password. Check your old password.");
        }
    }


}