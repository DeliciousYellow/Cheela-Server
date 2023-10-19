package com.delicious.controller;

import com.delicious.exception.CallServiceException;
import com.delicious.exception.ErrorException;
import com.delicious.exception.TokenException;
import com.delicious.feignService.FeignRoleService;
import com.delicious.feignService.FeignUserService;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.auth.Role;
import com.delicious.pojo.entity.user.User;
import com.delicious.service.LoginService;
import com.delicious.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/loginAbout")
public class LoginController {
    @Resource
    private FeignUserService feignUserService;
    @Resource
    private FeignRoleService feignRoleService;
    @Resource
    private LoginService loginService;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private HttpServletRequest request;

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('select:any')")
    public Result Hello() {
        return Result.build(ResultEnum.SELECT_SUCCESS).setMessage("HelloWorld");
    }

    @Operation(summary = "根据JWT查询用户信息和角色", method = "GET")
    @GetMapping("/getUserInfoAndRolesByToken")
    public Result getUserInfoAndRolesByToken() {
        String token = request.getHeader("X-Token");
        //从JWT中解析出来userId
        Integer userId = Integer.valueOf(JwtUtils.getClaimsByToken(token).getSubject());
        Result<User> userResult;
        Result<List<Role>> roleResult;
        try {
            userResult = feignUserService.baseQueryById(userId);
            roleResult = feignRoleService.QueryRolesByUserID(userId,request.getHeader("X-Token"));
        } catch (Exception e) {
            throw new CallServiceException();
        }
        if (ResultEnum.SELECT_SUCCESS.getCode().equals(userResult.getCode())
                && ResultEnum.SELECT_SUCCESS.getCode().equals(roleResult.getCode())){
            HashMap<String, Object> map = new HashMap<>();
            //为了安全密码设成空
            map.put("user",userResult.getData().setUserPassword(null));
            map.put("roles",roleResult.getData());
            return Result.build(ResultEnum.SELECT_SUCCESS,map);
        }else {
            return Result.build(ResultEnum.SELECT_FAIL);
        }
    }

    @Operation(summary = "用户登录", method = "POST")
    @PostMapping("/login")
    public Result UserLogin(@RequestBody User user) {
        return loginService.login(user);
    }

    @Operation(summary = "退出登录", method = "POST")
    @PostMapping("/logout")
    public Result UserLogout() {
        return loginService.logout();
    }

    @Operation(summary = "用户注册", method = "POST")
    @PostMapping("/register")
    public Result UserRegister(@RequestBody User user) throws ErrorException {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return feignUserService.baseAdd(user);
    }

    @RequestMapping("/error")
    //从过滤器处被拦截的http请求类型可能是多样的
    public Result throwException(HttpServletRequest request) {
        throw (TokenException) request.getAttribute("TokenException");
    }

}
