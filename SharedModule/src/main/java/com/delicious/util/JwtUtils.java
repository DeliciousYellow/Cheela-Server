package com.delicious.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtUtils {
//    static {
//        //类加载时读自己的配置文件
//        Properties properties = new Properties();
    //这里的config.properties好像必须写在调用模块的配置文件里
//        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
//            properties.load(fileInputStream);
//            expire = Long.parseLong(properties.getProperty("configuration_values.expire"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    //设置Token有效时间
    private static final long expire = 86400;
//    static byte[] secret = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
    static byte[] secret = "QWERTYUIOPASDFGHJKLZXCVCBNM1234567890QWERTYUIOPASDFGHJKLZXCVCBNM1234567890".getBytes();

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