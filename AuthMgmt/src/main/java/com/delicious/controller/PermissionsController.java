package com.delicious.controller;

import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Permission;
import com.delicious.service.PermissionsService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@RequestMapping("/permission")
@RestController
public class PermissionsController extends BaseController<PermissionsService, Permission> {

    private final PermissionsService permissionsService = service;//从父类继承过来的service

    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(Permission permission) {
        return super.baseQueryByEntity(permission);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(Permission entity, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(entity, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody Permission permission) {
        return super.baseAdd(permission);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody Permission permission) {
        return super.baseEdit(permission);
    }

    @Override
    @DeleteMapping("/{id}")
    protected Result baseDelById(@PathVariable String id) {
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        return super.baseDelByIds(ids);
    }
}
