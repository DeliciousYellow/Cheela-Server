package com.delicious.controller;


import com.delicious.exception.ErrorException;
import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.User;
import com.delicious.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */

@RequestMapping("/user")
@RestController
@Validated(AddAndEditGroup.class)
public class UserController extends BaseController<UserService, User> {
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(User user) {
        return super.baseQueryByEntity(user);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(User user, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(user, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody User user) throws ErrorException {
        return super.baseAdd(user);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) User user) throws ErrorException {
        return super.baseEdit(user);
    }

    @Override
    @DeleteMapping("/{id}")
    protected Result baseDelById(@PathVariable String id) throws ErrorException {
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    protected Result baseDelByIds(@RequestParam("ids") String ids) throws ErrorException {
        return super.baseDelByIds(ids);
    }
}
