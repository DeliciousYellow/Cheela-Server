package com.delicious.controller;

import com.delicious.pojo.entity.auth.Permission;
import com.delicious.pojo.entity.auth.Role;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/role")
public class RolesController extends BaseController<RolesService, Role> {

    @Resource
    private RolesService rolesService;

    @Operation(summary = "根据角色id查询对应的所有权限", method = "GET")
    @GetMapping("/QueryPermissionsByRoleId/{id}")
    protected Result QueryPermissionsByRoleId(@PathVariable Integer id) {
        List<Permission> permissions = rolesService.QueryPermissionsByRoleID(id);
        return Result.build(ResultEnum.SELECT_SUCCESS,permissions);
    }

    @Operation(summary = "根据用户id查询对应的所有权限", method = "GET")
    @GetMapping("/QueryPermissionsByUserID/{id}")
    protected Result QueryPermissionsByUserID(@PathVariable Integer id) {
        List<Permission> permissions = rolesService.QueryPermissionsByUserID(id);
        return Result.build(ResultEnum.SELECT_SUCCESS,permissions);
    }

    @Operation(summary = "根据用户id查询对应的角色", method = "GET")
    @GetMapping("/QueryRolesByUserID/{id}")
    protected Result QueryRolesByUserID(@PathVariable Integer id) {
        List<Role> roles = rolesService.QueryRolesByUserID(id);
        return Result.build(ResultEnum.SELECT_SUCCESS,roles);
    }

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
    protected Result baseDelById(@PathVariable Integer id) {
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        return super.baseDelByIds(ids);
    }
}
