package com.delicious.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    //设置Token有效时间
    private static final long expire = 60*60*24;
//    private static long expire = 2;
    private static final String secret = "abcdefghijklmnopqrstuvwxyz123456";

    //token生成
    public static String getToken(String UserId) {
        //获取当前时间
        Date now = new Date();
        //当前时间加上有效时间，得到过期时间
        Date end = new Date(now.getTime()+expire*1000);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(UserId)
                .setIssuedAt(now)
                .setExpiration(end)
                .signWith(SignatureAlgorithm.HS512,secret)//设置签名算法
                .compact();
    }

    //token解析
    public static Claims getClaimsByToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}