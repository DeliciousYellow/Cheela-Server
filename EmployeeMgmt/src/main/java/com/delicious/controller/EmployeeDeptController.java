package com.delicious.controller;

import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.EmployeeDept;
import com.delicious.service.EmployeeDeptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-15
 */
@RestController
@RequestMapping("/employeeDept")
public class EmployeeDeptController extends BaseController<EmployeeDeptService,EmployeeDept> {
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(EmployeeDept employeeDept) {
        return super.baseQueryByEntity(employeeDept);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(EmployeeDept employeeDept, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(employeeDept, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody EmployeeDept employeeDept) {
        return super.baseAdd(employeeDept);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) EmployeeDept employeeDept) {
        return super.baseEdit(employeeDept);
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
