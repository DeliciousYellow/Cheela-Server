package com.delicious.controller;

import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.workflow.WorkflowInfo;
import com.delicious.service.WorkflowInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-25
 */
@RestController
@RequestMapping("/workflowInfo")
public class WorkflowInfoController extends BaseController<WorkflowInfoService, WorkflowInfo> {
    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'select:any','select:workflow'})")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'select:any','select:workflow'})")
    protected Result baseQueryByEntity(WorkflowInfo workflowInfo) {
        return super.baseQueryByEntity(workflowInfo);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    @PreAuthorize("hasAnyAuthority({'select:any', 'select:workflow'})")
    protected Result baseQueryPageByEntity(WorkflowInfo workflowInfo, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(workflowInfo, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'insert:any','insert:workflow'})")
    protected Result baseAdd(@RequestBody @Validated(AddAndEditGroup.class) WorkflowInfo workflowInfo) {
        return super.baseAdd(workflowInfo);
    }

    @PostMapping("/AddWorkflowInfoAndSteps")
    @Transactional
    @PreAuthorize("hasAnyAuthority({'insert:any','insert:workflow'})")
    protected Result AddWorkflowInfoAndSteps(@RequestBody String resData) {
        return service.AddWorkflowInfoAndSteps(resData);
    }

    @Override
    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'update:any','update:workflow'})")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) WorkflowInfo workflowInfo) {
        return super.baseEdit(workflowInfo);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'delete:any','delete:workflow'})")
    protected Result baseDelById(@PathVariable Integer id) {
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'delete:any','delete:workflow'})")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        return super.baseDelByIds(ids);
    }
}
