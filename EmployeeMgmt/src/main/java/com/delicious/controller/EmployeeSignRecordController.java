package com.delicious.controller;

import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.EmployeeSignRecord;
import com.delicious.service.EmployeeSignRecordService;
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
@RequestMapping("/employee_sign_record")
public class EmployeeSignRecordController extends BaseController<EmployeeSignRecordService, EmployeeSignRecord>{
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(EmployeeSignRecord employeeSignRecord) {
        return super.baseQueryByEntity(employeeSignRecord);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(EmployeeSignRecord employeeSignRecord, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(employeeSignRecord, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody EmployeeSignRecord employeeSignRecord) {
        return super.baseAdd(employeeSignRecord);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) EmployeeSignRecord employeeSignRecord) {
        return super.baseEdit(employeeSignRecord);
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
