package com.delicious.controller;

import com.delicious.pojo.entity.auth.RelationUserRole;
import com.delicious.pojo.Result;
import com.delicious.service.RelationUserRolesService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-11
 */
@RestController
@RequestMapping("/relation_user_role")
public class RelationUserRolesController extends BaseController<RelationUserRolesService, RelationUserRole> {
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(RelationUserRole relationUserRole) {
        return super.baseQueryByEntity(relationUserRole);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(RelationUserRole relationUserRole, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(relationUserRole, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody RelationUserRole relationUserRole) {
        return super.baseAdd(relationUserRole);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody RelationUserRole relationUserRole) {
        return super.baseEdit(relationUserRole);
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
