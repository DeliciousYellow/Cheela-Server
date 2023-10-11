package com.delicious.controller;

import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Role;
import com.delicious.service.RolesService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/relation_role_permission")
public class RelationRolePermissionsController extends BaseController<RolesService, Role> {
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(Role role) {
        return super.baseQueryByEntity(role);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(Role role, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(role, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody Role role) {
        return super.baseAdd(role);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody Role role) {
        return super.baseEdit(role);
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
