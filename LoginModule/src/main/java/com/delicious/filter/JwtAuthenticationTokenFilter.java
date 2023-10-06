package com.delicious.filter;


import com.delicious.exception.CallServiceException;
import com.delicious.exception.TokenException;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.UserService;
import com.delicious.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String, User> redisTemplate_String_User;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate_String_Integer;
    @Resource
    private UserService userService;
    @Resource
    private ExecutorService executorService;

    /**
     * 在过滤器抛出的异常不会被@ControllerAdvice的异常处理类捕获
     * ①可以使用HandlerExceptionResolver
     * 在这里抛出对应的异常
     * handlerExceptionResolver.resolveException(request,response,null,new TokenException(ResultEnum.TOKEN_ERROR));
     * ②也可以手动的把异常交给Controller处理
     * 把异常记录到request中，然后转发请求到/error路径的控制器方法，由该方法抛出异常，最后统一异常处理捕获
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("X-Token");
        if (!StringUtils.hasText(token)) {
            //如果没有token直接放行，这个请求只会有登录的权限
            filterChain.doFilter(request, response);
            return;
        }
        Claims claimsByToken;
        try {
            claimsByToken = JwtUtils.getClaimsByToken(token);
        } catch (ExpiredJwtException e) {
            //JWT验证过期的情况
            request.setAttribute("TokenException", new TokenException(ResultEnum.TOKEN_TIMEOUT));
            request.getRequestDispatcher("/login/error").forward(request, response);
            return;
        } catch (SignatureException | MalformedJwtException e) {
            //JWT签名验证失败的情况|处理 JWT 格式错误的情况
            request.setAttribute("TokenException", new TokenException(ResultEnum.TOKEN_ERROR));
            request.getRequestDispatcher("/login/error").forward(request, response);
            return;
        }
        //获取userId
        int userId = Integer.parseInt(claimsByToken.getSubject());
        //获取redis中存储的最新版本号
        Integer redis_token_version = redisTemplate_String_Integer.opsForValue().get("token-version-id:"+userId);
        Integer request_token_version = claimsByToken.get("version", Integer.class);
        if (!request_token_version.equals(redis_token_version)){
            //如果token中的版本不是最新版本，不予放行
            request.setAttribute("TokenException", new TokenException(ResultEnum.TOKEN_TIMEOUT));
            request.getRequestDispatcher("/login/error").forward(request, response);
            return;
        }
        User user = redisTemplate_String_User.opsForValue().get("login:" + userId);
        if (Objects.isNull(user)) {
            //如果在token验证通过的情况下，在redis里却没有对应的数据，说明该数据在redis里过期了
            //此时要么重新登陆以存入redis，要么在这里查数据库重新交给redis
            Result result = userService.baseQueryById(userId);
            User DB_user;
            if (Objects.equals(result.getCode(), ResultEnum.SELECT_SUCCESS.getCode())) {
                //返回结果响应码为210,请求成功
                DB_user = (User) result.getData();
                //把数据库的DB_user存入Redis
                executorService.submit(() -> redisTemplate_String_User.opsForValue().set("login:" + DB_user.getUserId(), DB_user));
            } else {
                throw new CallServiceException();
            }
            user = DB_user;
        }
        //存入SecurityContextHolder
        //TODO 封装权限信息到  authenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
