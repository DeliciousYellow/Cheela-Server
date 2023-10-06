package com.delicious.service.impl;

import com.alibaba.fastjson.JSON;
import com.delicious.exception.LoginFailureException;
import com.delicious.pojo.LoginUserDetails;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.LoginService;
import com.delicious.util.JwtUtils;
import jakarta.annotation.Resource;
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

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisTemplate<String,User> redisTemplate_String_User;
    @Resource
    private RedisTemplate<String,Integer> redisTemplate_String_Integer;
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

            String token_version = "token-version-id:"+ principal.getUser().getUserId().toString();
            // 获取令牌版本号
            Integer version = redisTemplate_String_Integer.opsForValue().get(token_version);
            if (Objects.isNull(version)) {
                //如果没有对应的版本信息则下一次发布版本-1
                version = 1;
            }
            //生成token
            String token = JwtUtils.getToken(principal.getUser().getUserId().toString(),version);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            //把完整用户信息一并返回
            resultMap.put("user", JSON.toJSONString(principal.getUser()));
            //更新redis中的令牌版本
            final Integer finalVersion = version;//编译器说需要为final
            executorService.submit(()->redisTemplate_String_Integer.opsForValue().set(token_version, finalVersion));
            //提交任务给线程池，异步操作redis
            executorService.submit(()->redisTemplate_String_User.opsForValue().set("login:"+principal.getUser().getUserId(),principal.getUser()));

            return Result.build(ResultEnum.LOGIN_SUCCESS, resultMap);
        }
        throw new LoginFailureException("登录失败");
    }

    @Override
    public Result logout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User principal = (User) authentication.getPrincipal();
            String token_version = "token-version-id:"+ principal.getUserId().toString();
            // 获取令牌版本号
            Integer version = redisTemplate_String_Integer.opsForValue().get(token_version);
            redisTemplate_String_Integer.opsForValue().set(token_version,version+1);
        } catch (Exception e) {
            return Result.build(ResultEnum.LOGOUT_FAIL);
        }
        return Result.build(ResultEnum.LOGOUT_SUCCESS);
    }
}
