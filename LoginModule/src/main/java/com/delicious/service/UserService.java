package com.delicious.service;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "UserMgmt",path = "/UserMgmt/user")
public interface UserService {

    @GetMapping("/{id}")
    Result<User> baseQueryById(@PathVariable Integer id);

    @GetMapping("/")
    Result<User> baseQueryByEntity(User user);
    @GetMapping("/{pageIndex}/{pageSize}")
    Result<User> baseQueryPageByEntity(User user, @PathVariable int pageIndex, @PathVariable int pageSize);

    @PostMapping("/")
    Result<User> baseAdd(@RequestBody User user) throws ErrorException;

    @PutMapping("/")
    Result<User> baseEdit(@RequestBody User user) throws ErrorException;

    @DeleteMapping("/{id}")
    Result<User> baseDelById(@PathVariable String id) throws ErrorException;

    @DeleteMapping("/")
    Result<User> baseDelByIds(@RequestParam("ids") String ids) throws ErrorException;
}
