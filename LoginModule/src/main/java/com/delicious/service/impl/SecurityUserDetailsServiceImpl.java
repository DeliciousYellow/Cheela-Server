package com.delicious.service.impl;

import com.delicious.exception.LoginFailureException;
import com.delicious.pojo.LoginUserDetails;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.User;
import com.delicious.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
        //查用户信息
        User build = User.builder().userPhone(userPhone).build();
        Result<List<User>> result = userService.baseQueryByEntity(build);

//        Result<User> result = userService.baseQueryById(30);
        if (Objects.equals(result.getCode(), ResultEnum.SELECT_SUCCESS.getCode())) {
            //返回结果响应码为210,即这次查询请求本身是成功的
            //判断是否查到了某个已存在的用户
            if (result.getData().size() == 0) {
                //没查到，就告诉用户需要注册
                throw new LoginFailureException("没有对应用户，请先注册");
            }
            //把数据封装成UserDetails返回
            User user = result.getData().get(0);
            return LoginUserDetails.builder().user(user).build();
        } else {
            //请求都失败了
            throw new LoginFailureException("查询对应用户时未响应，请重试");
        }
    }
}