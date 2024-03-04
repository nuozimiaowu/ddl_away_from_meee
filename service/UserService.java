package com.example.cooperation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperation.common.Result;
import com.example.cooperation.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User>{
    void update(User user);

    Result<User> selectAll();

    User findByUsername(User user);

    User findUserById(String userId);

    User userLoginNow(HttpServletRequest request);

    //修改用户的密码的方法
    boolean changePassword(String userId, String oldPassword, String newPassword);

    // 新增方法来检查用户名是否已存在
    boolean existsByUserName(String userName);
}
