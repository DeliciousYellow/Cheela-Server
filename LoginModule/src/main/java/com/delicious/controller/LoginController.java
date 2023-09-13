package com.delicious.controller;

import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @Operation(summary = "用户登录", method = "POST")
    @PostMapping("/login")
    public Result AdminLogin(@RequestBody User user) {
        return Result.build(ResultEnum.ERROR);
    }
}
