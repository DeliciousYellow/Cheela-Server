package com.delicious.controller;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Roles;
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
@RequestMapping("/relation_role_permissions")
public class RelationRolePermissionsController extends BaseController<RolesService, Roles> {
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(Roles roles) {
        return super.baseQueryByEntity(roles);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(Roles roles, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(roles, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody Roles roles) throws ErrorException{
        return super.baseAdd(roles);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody Roles roles) throws ErrorException{
        return super.baseEdit(roles);
    }

    @Override
    @DeleteMapping("/{id}")
    protected Result baseDelById(@PathVariable String id) throws ErrorException{
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    protected Result baseDelByIds(@RequestParam("ids") String ids) throws ErrorException {
        return super.baseDelByIds(ids);
    }
}
