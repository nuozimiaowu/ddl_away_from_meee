package com.example.cooperation.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cooperation.annotation.UserLoginToken;
import com.example.cooperation.common.Result;
import com.example.cooperation.mapper.ArticleMapper;
import com.example.cooperation.pojo.Article;
import com.example.cooperation.pojo.User;
import com.example.cooperation.service.ArticleService;
import com.example.cooperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@UserLoginToken
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("/new")
    public Result<String> newArticle(@RequestBody Article article, HttpServletRequest request){
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token error");
        }
        // 设置文章的发布时间为当前时间
        article.setPublishTime(LocalDateTime.now());

        // 初始化浏览量为0
        article.setViews(0);

        // 如果User对象包含作者的信息，设置作者
        article.setAuthorId(user.getUserId()); // 假设User对象有getUsername()方法
        article.setAuthorName(user.getUserName());

        // 保存文章到数据库
        articleService.save(article);

        // 返回成功结果，这里的Result是假设你有一个用于封装响应的Result类
        return Result.success("Article created successfully");
    }

    @CrossOrigin
    @PutMapping("/click")
    public Result<String> clickArticle(@RequestBody Article article, HttpServletRequest request){
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token error");
        }
        // 检查传入的Article对象是否有效
        if (article == null) {
            return Result.error("Invalid article data");
        }
        // 从数据库中获取当前文章
        Article currentArticle = articleService.getById(article.getId());
        if (currentArticle == null) {
            return Result.error("Article not found");
        }
        // 更新浏览量
        currentArticle.setViews(currentArticle.getViews() + 1);
        // 保存更新到数据库
        boolean updateSuccess = articleService.updateById(currentArticle);
        if (!updateSuccess) {
            return Result.error("Failed to update article views");
        }
        // 返回成功结果
        return Result.success("Article views updated successfully");
    }


    @CrossOrigin
    @GetMapping("/ranking_list")
    public Result<Page<Article>> rankingList(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        // 使用前端传递的页码和每页大小进行分页
        Page<Article> page = new Page<>(pageNum, pageSize);
        // 创建查询条件，根据views降序排序
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("views");
        // 执行分页查询
        Page<Article> result = articleService.page(page, queryWrapper);
        // 将结果封装到Result对象中并返回
        return Result.success(result);
    }



    @CrossOrigin
    @GetMapping("/current_list")
    public Result<Page<Article>> CurrentList() {
        // 创建分页构造器，第1页，每页10条数据
        Page<Article> page = new Page<>(1, 10);
        // 创建查询条件，根据publishTime降序排序
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("publish_time");
        // 执行分页查询
        Page<Article> result = articleService.page(page, queryWrapper);
        // 将结果封装到Result对象中并返回
        return Result.success(result);
    }
    @CrossOrigin
    @GetMapping("/find_article_by_id")
    public Result<Article> getArticleById(@RequestParam("id") int id){
        // 使用ArticleService通过ID查询文章
        Article article = articleService.getById(id);

        if (article != null) {
            // 如果找到了文章，返回成功结果
            return Result.success(article);
        } else {
            // 如果没有找到文章，返回错误信息
            return Result.error("Article not found with id: " + id);
        }

    }
}
