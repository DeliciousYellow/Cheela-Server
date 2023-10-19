package com.delicious.service.impl;

import com.alibaba.fastjson.JSON;
import com.delicious.pojo.LoginUserDetails;
import com.delicious.pojo.entity.user.User;
import com.delicious.exception.LoginFailureException;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.service.LoginService;
import com.delicious.util.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static com.delicious.pojo.ShareTokenVersion.VersionMap;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisTemplate<String, LoginUserDetails> redisTemplate_String_LoginUserDetails;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate_String_Integer;
    @Resource
    private ExecutorService executorService;

    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserPhone(), user.getUserPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new LoginFailureException(e.getMessage());
        }
        if (!Objects.isNull(authentication)) {
            LoginUserDetails principal = (LoginUserDetails) authentication.getPrincipal();
            //拼接获取/存储token版本的key值
            String token_version = "token-version-id:" + principal.getUser().getUserId().toString();
            // 获取令牌版本号
            Integer version = null;
            try {
                version = redisTemplate_String_Integer.opsForValue().get(token_version);
            } catch (Exception e) {
                log.info("redis操作失败，错误信息：" + e.getMessage());
            }
            if (Objects.isNull(version)) {
                //如果没有对应的版本信息则下一次发布版本-1
                version = 1;
            }
            //生成token
            String token = JwtUtils.getToken(principal.getUser().getUserId().toString(), version);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            //把完整用户信息一并返回
            resultMap.put("user", JSON.toJSONString(principal.getUser()));
            //更新令牌版本
            final Integer finalVersion = version;//编译器说需要为final
            executorService.submit(() -> {
                //更新静态变量(在请求redis失效时用于验证版本)
                VersionMap.put(token_version, finalVersion);
                //更新redis中的
                redisTemplate_String_Integer.opsForValue().set(token_version, finalVersion);
            });
            //更新redis中的LoginUserDetails
            executorService.submit(() -> redisTemplate_String_LoginUserDetails.opsForValue().set("login:" + principal.getUser().getUserId(), principal));
            return Result.build(ResultEnum.LOGIN_SUCCESS, resultMap);
        }
        throw new LoginFailureException("登录失败");
    }

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String token_version = "token-version-id:" + principal.getUserId().toString();
        // 更新静态变量的令牌版本号
        Integer static_version = VersionMap.get(token_version);
        VersionMap.put(token_version, static_version + 1);
        try {
            // 更新redis的令牌版本号
            Integer redis_version = redisTemplate_String_Integer.opsForValue().get(token_version);
            if (!Objects.isNull(redis_version)){
                redisTemplate_String_Integer.opsForValue().set(token_version, redis_version + 1);
            }
        } catch (Exception e) {
            log.info("redis操作失败，错误信息："+e.getMessage());
        }
        return Result.build(ResultEnum.LOGOUT_SUCCESS);
    }
}
