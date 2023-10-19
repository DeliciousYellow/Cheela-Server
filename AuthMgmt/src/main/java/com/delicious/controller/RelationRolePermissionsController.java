package com.delicious.controller;

import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.auth.RelationRolePermission;
import com.delicious.service.RelationRolePermissionsService;
import jakarta.annotation.Resource;
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
public class RelationRolePermissionsController extends BaseController<RelationRolePermissionsService, RelationRolePermission> {

    @Resource
    private RelationRolePermissionsService relationRolePermissionsService;

    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(RelationRolePermission relationRolePermission) {
        return super.baseQueryByEntity(relationRolePermission);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(RelationRolePermission relationRolePermission, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(relationRolePermission, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody RelationRolePermission relationRolePermission) {
        return super.baseAdd(relationRolePermission);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody RelationRolePermission relationRolePermission) {
        return super.baseEdit(relationRolePermission);
    }

    @Override
    @DeleteMapping("/{id}")
    protected Result baseDelById(@PathVariable Integer id) {
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    protected Result DelByEntity(@RequestBody RelationRolePermission relationRolePermission) {
        int number = relationRolePermissionsService.DelByEntity(relationRolePermission);
        if (number!=1){
            return Result.build(ResultEnum.DELETE_FAIL);
        }
        return Result.build(ResultEnum.DELETE_SUCCESS);
    }
    @Override
    @DeleteMapping("/batch/")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        return super.baseDelByIds(ids);
    }
}
