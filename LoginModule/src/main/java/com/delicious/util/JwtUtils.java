package com.delicious.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${configuration_values.expire}")
    private void SetExpire(long configuration_values_expire){
        //服务启动时读取配置文件获取token过期时间
        expire = configuration_values_expire;
    }
    //设置Token有效时间
    private static long expire;
    static byte[] secret = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();

    //token生成
    public static String getToken(String UserId,Integer version) {
        //获取当前时间
        Date now = new Date();
        //当前时间加上有效时间，得到过期时间
        Date end = new Date(now.getTime()+expire*1000);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(UserId)
                .claim("version",version) //添加版本字段
                .setIssuedAt(now)
                .setExpiration(end)
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)//设置签名算法
                .compact();
    }

    //token解析
    public static Claims getClaimsByToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}