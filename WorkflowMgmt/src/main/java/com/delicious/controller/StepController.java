package com.delicious.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.delicious.exception.ErrorException;
import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.workflow.Step;
import com.delicious.pojo.entity.workflow.WorkflowInfo;
import com.delicious.service.StepService;
import com.delicious.service.WorkflowInfoService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-06
 */
@RestController
@RequestMapping("/step")
public class StepController extends BaseController<StepService, Step>{

    @Resource
    private WorkflowInfoController workflowInfoController;
    @Resource
    private WorkflowInfoService workflowInfoService;

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'select:any','select:step'})")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority({'select:any','select:step'})")
    protected Result baseQueryByEntity(Step step) {
        return super.baseQueryByEntity(step);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    @PreAuthorize("hasAnyAuthority({'select:any','select:step'})")
    protected Result baseQueryPageByEntity(Step step, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(step, pageIndex, pageSize);
    }

    @Override
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority({'insert:any','insert:step'})")
    protected Result baseAdd(@RequestBody @Validated(AddAndEditGroup.class) Step step) {
        return super.baseAdd(step);
    }

    @Override
    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority({'update:any','update:step'})")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) Step step) {
        LambdaQueryWrapper<WorkflowInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkflowInfo::getWorkflowCode,step.getWorkflowCode());
        WorkflowInfo workflowInfo = null;
        try {
            workflowInfo = workflowInfoService.list(wrapper).get(0);
        } catch (Exception e) {
            throw new ErrorException(e);
        }
        //修改step数据的前提是其对应工作流已开始
        if ("未开始".equals(workflowInfo.getWorkflowState())) {
            return Result.build(ResultEnum.UPDATE_FAIL).setMessage("该工作流未开始");
        }
        //修改step数据的前提是其对应工作流未过期
        if ("已过期".equals(workflowInfo.getWorkflowState())) {
            return Result.build(ResultEnum.UPDATE_FAIL).setMessage("该工作流已过期");
        }

        if ("流程结束".equals(step.getStepName())) {
            //如果该次推进是最后一个步骤且工作流未过期,就尝试完成整个工作流
            try {
                workflowInfoController.baseEdit(workflowInfo.setWorkflowState("已完成"));
            } catch (Exception e) {
                return super.baseEdit(step);
            }
        }
        return super.baseEdit(step);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'delete:any','delete:step'})")
    protected Result baseDelById(@PathVariable Integer id) {
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority({'delete:any','delete:step'})")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        return super.baseDelByIds(ids);
    }
}
