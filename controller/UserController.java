package com.example.project4.controller;
import com.alibaba.fastjson.JSONObject;
import com.example.project4.annotation.UserLoginToken;
import com.example.project4.common.Result;
import com.example.project4.pojo.UserDto;
import com.example.project4.service.UserService;
import com.example.project4.service.impl.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    @UserLoginToken
    @PutMapping
    @CrossOrigin
    public Result<String> update(@RequestBody UserDto userDto){
        userService.update(userDto);
        return Result.success("修改成功");
    }

    @GetMapping("all")
    @CrossOrigin
    public Result<UserDto> selectAll(){
        return userService.selectAll();
    }

  /*  @PutMapping("login")
    public Result login(@RequestBody UserDto userDto, HttpSession session){
        UserDto user = userService.
                query().
                eq("user_name", userDto.getUserName()).
                one();
        if(user == null){
            return Result.error("账号不存在");
        }
        if (!StringUtils.equals(user.getUserPassword(),userDto.getUserPassword())){
            return Result.error("密码错误");
        }
        //登录成功以后存入session
        session.setAttribute("user",user);
        return Result.success(user);
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin
    public Result<Object> login(@RequestBody UserDto user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();

        // 获取用户账号密码
        UserDto userForBase = userService.findByUsername(user);

        // 判断账号或密码是否正确
        if (userForBase == null || !userForBase.getUserPassword().equals(user.getUserPassword())) {
            return Result.error("LOGIN FAILURE");
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);

            return Result.success(jsonObject);
        }
    }


    @PostMapping("register")
    @CrossOrigin
    public Result save(@RequestBody UserDto userDto){
        boolean save = userService.save(userDto);
        if (!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

}
