package com.delicious.controller;

import com.delicious.exception.ErrorException;
import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.WorkflowInfo;
import com.delicious.service.WorkflowInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-25
 */
@RestController
@RequestMapping("/workflowInfo")
public class WorkflowInfoController extends BaseController<WorkflowInfoService, WorkflowInfo>{
    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(WorkflowInfo workflowInfo) {
        return super.baseQueryByEntity(workflowInfo);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(WorkflowInfo workflowInfo, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(workflowInfo, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody @Validated(AddAndEditGroup.class) WorkflowInfo workflowInfo) throws ErrorException {
        return super.baseAdd(workflowInfo);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) WorkflowInfo workflowInfo) throws ErrorException {
        return super.baseEdit(workflowInfo);
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
