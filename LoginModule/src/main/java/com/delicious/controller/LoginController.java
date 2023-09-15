package com.delicious.controller;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Operation(summary = "用户登录", method = "POST")
    @PostMapping("/login")
    public Result UserLogin(@RequestBody User user) {
        return Result.build(ResultEnum.ERROR);
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

}
