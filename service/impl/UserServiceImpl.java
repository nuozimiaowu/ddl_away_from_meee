package com.example.cooperation.service.impl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperation.common.Result;
import com.example.cooperation.mapper.UserMapper;
import com.example.cooperation.pojo.User;
import com.example.cooperation.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Autowired
    UserMapper userMapper;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public void update(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", user.getUserId()); // 使用userId作为更新条件

        // 检查每个字段是否为null，不为null则添加到更新条件中
        if (user.getUserName() != null) {
            updateWrapper.set("user_name", user.getUserName());
        }
        if (user.getAvatarUrl() != null) {
            updateWrapper.set("avatar_url", user.getAvatarUrl());
        }
        if (user.getIntroduction() != null) {
            updateWrapper.set("introduction", user.getIntroduction());
        }

        // 使用updateWrapper执行更新操作
        userMapper.update(null, updateWrapper); // 第一个参数为null，表示不根据实体更新
    }

    @Override
    public Result<User> selectAll() {
        return null;
    }

    @Override
    public User findByUsername(User user) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName,user.getUserName()));
    }

    @Override
    public User findUserById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User userLoginNow(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }

        String userId = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);
        User user = findUserById(userId);
        return user;
    }

    @Override
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        // 通过 userId 获取用户信息
        User user = getById(userId);
        if (user == null) {
            // 用户不存在
            return false;
        }
        // 检查原密码是否正确
        if (!user.getUserPassword().equals(oldPassword)) {
            // 原密码不正确
            return false;
        }
        // 更新密码
        user.setUserPassword(newPassword);
        return updateById(user); // updateById 返回一个 boolean 值，指示操作是否成功
    }

    //检验用户名是否存在
    @Override
    public boolean existsByUserName(String userName) {
        // 使用userMapper来检查数据库中是否存在该用户名
        // 这里的实现取决于你使用的数据库访问框架
        // 例如，使用MyBatis Plus，你可能会这样实现：
        return userMapper.selectCount(new QueryWrapper<User>().eq("user_name", userName)) > 0;
    }
}
