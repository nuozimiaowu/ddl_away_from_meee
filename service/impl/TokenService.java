package com.example.project4.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project4.pojo.UserDto;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：Mr.ZJW
 * @date ：Created 2022/2/28 10:20
 * @description：
 */
@Service
public class TokenService {

    public String getToken(UserDto user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUserId()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }
}
