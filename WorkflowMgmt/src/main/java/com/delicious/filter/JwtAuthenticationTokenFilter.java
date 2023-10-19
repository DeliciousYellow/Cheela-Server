package com.delicious.filter;


import com.delicious.exception.CallServiceException;
import com.delicious.exception.TokenException;
import com.delicious.feignService.FeignRoleService;
import com.delicious.feignService.FeignUserService;
import com.delicious.pojo.LoginUserDetails;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.auth.Permission;
import com.delicious.pojo.entity.user.User;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static com.delicious.pojo.ShareTokenVersion.VersionMap;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String, LoginUserDetails> redisTemplate_String_LoginUserDetails;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate_String_Integer;
    @Resource
    private FeignUserService feignUserService;
    @Resource
    private FeignRoleService roleService;
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
            request.getRequestDispatcher("/filterException/error").forward(request, response);
            return;
        } catch (SignatureException | MalformedJwtException e) {
            //JWT签名验证失败的情况|处理 JWT 格式错误的情况
            request.setAttribute("TokenException", new TokenException(ResultEnum.TOKEN_ERROR));
            request.getRequestDispatcher("/filterException/error").forward(request, response);
            return;
        }
        //获取userId
        int userId = Integer.parseInt(claimsByToken.getSubject());
        //查静态变量的版本信息，如果查询redis失败，直接使用静态变量版本信息
        Integer token_version = VersionMap.get("token-version-id:" + userId);
        try {
            Integer redis_token_version = redisTemplate_String_Integer.opsForValue().get("token-version-id:" + userId);
            if (!Objects.isNull(redis_token_version)){
                //如果redis某段时间崩了，静态变量的版本信息可能会大于redis中的
                //恢复连接之后，把静态变量的版本信息重新提交到redis里。
                if (Objects.isNull(token_version)){
                    //如果静态变量版本信息是空，则从redis拉取存到静态变量
                    //这里如果是非LoginModule模块的话，这个VersionMap就是空
                    //所以如果redis崩了，其他模块第一次被调用时一定会访问失败
                    //这个VersionMap不是一个跨服务的共享变量，所以更完备的做法是，把这个Version版本在数据库也存一份
                    //如果redis崩了，就用数据库全局同步版本信息
                    VersionMap.put("token-version-id:" + userId,redis_token_version);
                }else {
                    token_version = token_version > redis_token_version ? token_version : redis_token_version;
                    Integer finalToken_version = token_version;
                    executorService.submit(()->redisTemplate_String_Integer.opsForValue().set("token-version-id:"+userId, finalToken_version));
                }
            }
        } catch (Exception e) {
            log.info("redis操作失败，错误信息："+e.getMessage());
        }
        Integer request_token_version = claimsByToken.get("version", Integer.class);
        if (!request_token_version.equals(token_version)) {
            //如果token中的版本不是最新版本，不予放行
            request.setAttribute("TokenException", new TokenException(ResultEnum.TOKEN_TIMEOUT));
            request.getRequestDispatcher("/filterException/error").forward(request, response);
            return;
        }
        LoginUserDetails loginUserDetails = null;
        try {
            loginUserDetails = redisTemplate_String_LoginUserDetails.opsForValue().get("login:" + userId);
        } catch (Exception e) {
            log.info("redis操作失败，错误信息："+e.getMessage());
        }
        if (Objects.isNull(loginUserDetails)) {
            //如果在token验证通过的情况下，在redis里却没有对应的数据，说明该数据在redis里过期了
            //此时要么重新登陆以存入redis，要么在这里查数据库重新交给redis
            //为了避免redis崩了所有鉴权都无法通过的情况，这里选择查数据库
            Result result = feignUserService.baseQueryById(userId);
            LoginUserDetails DB_loginUserDetails;
            if (Objects.equals(result.getCode(), ResultEnum.SELECT_SUCCESS.getCode())) {
                //返回结果响应码为210,请求成功
                User DB_user = (User) result.getData();
                //查询用户拥有的权限信息到封装到 LoginUserDetails
                Result<List<Permission>> permissionResult = roleService.QueryPermissionsByUserID(DB_user.getUserId(),request.getHeader("X-Token"));
                List<Permission> permissions;
                if (Objects.equals(permissionResult.getCode(), ResultEnum.SELECT_SUCCESS.getCode())) {
                    permissions = permissionResult.getData();
                } else {
                    throw new CallServiceException();
                }
                DB_loginUserDetails = LoginUserDetails.builder().user(DB_user).permissions(permissions).build();
                //把数据库查出来的用户信息和权限信息一起封装成LoginUserDetails再存入Redis
                executorService.submit(() -> redisTemplate_String_LoginUserDetails.opsForValue().set("login:" + DB_user.getUserId(), DB_loginUserDetails));
            } else {
                throw new CallServiceException();
            }
            loginUserDetails = DB_loginUserDetails;
        }
        //在请求进入servlet容器时，查询该请求token中的用户所拥有的权限信息，存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginUserDetails.getUser(),
                        null,
                        loginUserDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
