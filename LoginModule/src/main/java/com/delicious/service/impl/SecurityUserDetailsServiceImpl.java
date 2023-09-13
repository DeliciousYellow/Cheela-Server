package com.delicious.service.impl;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.LoginUserDetails;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
        //查用户信息
        Result<User> result = userService.baseQueryByEntity(User.builder().userPhone(userPhone).build());
        if (Objects.equals(result.getCode(), ResultEnum.SELECT_SUCCESS.getCode())) {
            //返回结果响应码为210,即这次查询请求本身是成功的。
            //判断是否查到了某个已存在的用户
            User user = result.getData();
            if (Objects.isNull(user)) {
                //没查到，就告诉用户需要注册
                throw new RuntimeException("没有对应用户，请先注册");
            }
            //把数据封装成UserDetails返回
            return LoginUserDetails.builder().user(user).build();
        }
        else {
            //请求都失败了
            throw new RuntimeException("查询对应用户时错误");
        }
        //查对应的权限信息
    }
}