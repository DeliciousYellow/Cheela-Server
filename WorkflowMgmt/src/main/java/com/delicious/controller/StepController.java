package com.delicious.controller;


import com.delicious.exception.ErrorException;
import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Step;
import com.delicious.service.StepService;
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
@RequestMapping("/steps")
public class StepController extends BaseController<StepService, Step>{

    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(Step step) {
        return super.baseQueryByEntity(step);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(Step step, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(step, pageIndex, pageSize);
    }

    @PostMapping("/")
    protected Result AddSteps(@RequestBody Step step) throws ErrorException {
        return super.baseAdd(step);
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) Step step) throws ErrorException {
        return super.baseEdit(step);
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
