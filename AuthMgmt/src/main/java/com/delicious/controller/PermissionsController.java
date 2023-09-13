package com.delicious.controller;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Permissions;
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
public class PermissionsController extends BaseController<PermissionsService, Permissions> {

    private final PermissionsService permissionsService = service;//从父类继承过来的service

    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(Permissions permissions) {
        return super.baseQueryByEntity(permissions);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(Permissions entity, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(entity, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody Permissions permissions) throws ErrorException {
        return super.baseAdd(permissions);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody Permissions permissions) throws ErrorException {
        return super.baseEdit(permissions);
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
