package com.delicious.service;

import com.delicious.pojo.entity.user.User;
import com.delicious.pojo.Result;

public interface LoginService {
    Result login(User user);

    Result logout();
}
