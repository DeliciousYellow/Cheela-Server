package com.delicious.service;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "UserMgmt",path = "/user")
public interface FeignUserService {

    @GetMapping("/{id}")
    Result<User> baseQueryById(@PathVariable Integer id);

    @GetMapping("/")
    Result<List<User>> baseQueryByEntity(@SpringQueryMap User user);
    @GetMapping("/{pageIndex}/{pageSize}")
    Result<List<User>> baseQueryPageByEntity(User user, @PathVariable int pageIndex, @PathVariable int pageSize);

    @PostMapping("/")
    Result<User> baseAdd(@RequestBody @Validated(AddAndEditGroup.class) User user) throws ErrorException;

    @PutMapping("/")
    Result<User> baseEdit(@RequestBody @Validated(AddAndEditGroup.class) User user) throws ErrorException;

    @DeleteMapping("/{id}")
    Result<User> baseDelById(@PathVariable String id) throws ErrorException;

    @DeleteMapping("/")
    Result<User> baseDelByIds(@RequestParam("ids") String ids) throws ErrorException;
}
