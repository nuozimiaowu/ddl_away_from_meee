package com.example.cooperation.service.impl;
import com.example.cooperation.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class TokenService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String getToken(User user) {
        // 设置Token的有效期为1小时
        long expirationTimeMillis = System.currentTimeMillis() + 60 * 60 * 1000;

        // 创建Token的Payload，这里可以存放一些额外的信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());

        // 使用JWT库创建Token
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserId())  // 设置Token的主题，可以是用户名或其他标识符
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // 使用提供的密钥进行签名
                .compact();

        return token;
    }

}
