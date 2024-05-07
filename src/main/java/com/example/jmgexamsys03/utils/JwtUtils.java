package com.example.jmgexamsys03.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 生成token和验证token的工具类
public class JwtUtils {
    private static final String jwtToken = "suiyi";

    /**
     * @param uid 当前用户的uid
     * @return 返回根据这个uid生成的token
     */
    public static String createToken(long uid){
        // 创建一个map,用来存储token中的载荷（payload）
        Map<String,Object> claims =new HashMap<>();
        claims.put("uid",uid);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置有效载荷
                .setClaims(claims)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 设置过期时间 (一天)
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000))
                // 采用HS256方式签名，key就是用来签名的秘钥
                .signWith(SignatureAlgorithm.HS256, jwtToken);
        String token = jwtBuilder.compact();
        return token;
    }

    //检查Token
    public static Map<String, Object> checkToken(String token) {
        try {
            // 对jwt进行解析和验证
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            // 返回的是token中存储的载荷
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
