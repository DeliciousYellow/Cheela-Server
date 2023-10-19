package com.delicious.controller;

import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.employee.EmployeeInfo;
import com.delicious.service.EmployeeInfoService;
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
@RequestMapping("/employeeInfo")
public class EmployeeInfoController extends BaseController<EmployeeInfoService, EmployeeInfo> {
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(EmployeeInfo employeeInfo) {
        return super.baseQueryByEntity(employeeInfo);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(EmployeeInfo employeeInfo, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(employeeInfo, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody EmployeeInfo employeeInfo) {
        return super.baseAdd(employeeInfo);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) EmployeeInfo employeeInfo) {
        return super.baseEdit(employeeInfo);
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
