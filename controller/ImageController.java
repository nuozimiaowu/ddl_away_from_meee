package com.example.cooperation.controller;

import com.auth0.jwt.JWT;
import com.example.cooperation.annotation.UserLoginToken;
import com.example.cooperation.common.Result;
import com.example.cooperation.mapper.UserMapper;
import com.example.cooperation.pojo.User;
import com.example.cooperation.service.UserService;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 处理文件上传下载
 */
@UserLoginToken
@RestController
@RequestMapping("common")
public class ImageController {

    //从配置文件里拿到存储图片的地址实现解耦
    @Value("${wop.path}")
    private String basePath;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private UserService userService; // UserService注入

    @Autowired
    private UserMapper userMapper; // UserService注入
    /*
     * 文件上传
     * 1.上传的目录需要配置
     * 2.上传的文件名不能重复
     * 3.需要把生成的文件名响应到客户端
     * */
    @CrossOrigin
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file,HttpServletRequest request) throws IOException {
        //对于token做检验：
        User user = userService.userLoginNow(request);
        if (user == null){
            return Result.error("token不存在");
        }
        //token检验完成。
        //MultipartFile表示的文件上传的内容，临时文件
        //判断储存的目录是不是存在，不存在就创建
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //要生成唯一的UUID文件名:
        String uuid = UUID.randomUUID().toString();
        //获取源文件最后一个点的索引
        int index = file.getOriginalFilename().lastIndexOf(".");
        //获取源文件的后缀名字
        String suffix = file.getOriginalFilename().substring(index);
        //拼接出来一个唯一的文件名
        String uuidFileName = uuid+suffix;
        //把这个临时文件存储到指定位置,抛出异常
        file.transferTo(new File(basePath+"/"+uuidFileName));

        //下面就要把这个图片名字存入数据库。
        user.setAvatarUrl(uuidFileName);
        userService.update(user);
        return Result.success(uuidFileName);

    }


    //文件的下载：文件数据的回显。
    @CrossOrigin
    @GetMapping("download")
    public void download(String id, HttpServletResponse response) throws IOException {
        //文件的下载：把文件以流的形式写给客户端
        //输入流：文件的流        输出流：response.getOutputStream
        User user = userService.getById(id);
        FileInputStream is = new FileInputStream(basePath+"/"+user.getAvatarUrl());
        ServletOutputStream outputStream = response.getOutputStream();
        //输入流和输出流对接：
        IOUtils.copy(is,outputStream);
        is.close();
    }
}






