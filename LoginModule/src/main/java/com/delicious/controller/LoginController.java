package com.delicious.controller;

import com.delicious.exception.ErrorException;
import com.delicious.exception.TokenException;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.User;
import com.delicious.service.LoginService;
import com.delicious.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private LoginService loginService;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

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
        Result<User> result;
        try {
            result = userService.baseAdd(user);
        } catch (ErrorException e) {
            throw e;
        }
        return result;
    }

    @PostMapping("/error")
    public Result throwException(HttpServletRequest request) {
        throw (TokenException) request.getAttribute("TokenException");
    }

}
