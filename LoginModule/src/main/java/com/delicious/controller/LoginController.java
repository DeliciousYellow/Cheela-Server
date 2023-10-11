package com.delicious.controller;

import com.delicious.exception.ErrorException;
import com.delicious.exception.TokenException;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.FeignUserService;
import com.delicious.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loginAbout")
public class LoginController {
    @Resource
    private FeignUserService feignUserService;
    @Resource
    private LoginService loginService;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('update:notice')")
    public  Result Hello(){
        return Result.build(ResultEnum.SELECT_SUCCESS).setMessage("HelloWorld");
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
