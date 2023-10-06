package com.delicious.service;

import com.delicious.pojo.Result;
import com.delicious.pojo.entity.User;

public interface LoginService {
    Result login(User user);

    Result logout();
}
