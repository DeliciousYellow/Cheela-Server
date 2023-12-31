package com.delicious.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.delicious.exception.ErrorException;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.BaseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public abstract class BaseController<S extends IService<M>, M extends BaseEntity> {

    @Autowired
    protected S service;

    @Operation(summary = "基础功能-通过ID查询单条记录", method = "GET")
    protected Result baseQueryById(@PathVariable("id") Integer id) {
        if (id == null) {
            return Result.build(ResultEnum.SELECT_FAIL).setMessage("id不能为空！");
        }
        M m = service.getById(id);
        return Result.build(ResultEnum.SELECT_SUCCESS, m);
    }

    @Operation(summary = "基础功能-条件查询", method = "GET")
    protected Result baseQueryByEntity(M entity) {
        if (entity.IsNull()) {
            Page<M> page = service.page(new Page<>(1, 10));
            List<M> list = page.getRecords();
            //TODO 按时间降序排序，获取最近的10条记录
            return Result.build(ResultEnum.SELECT_SUCCESS, list).setMessage("查询成功，但没有查询条件生效，记录总条数为：" + page.getTotal() + "，已返回" + list.size() + "条");
        }
        LambdaQueryWrapper<M> wrapper = new LambdaQueryWrapper<>();
        List<M> list = service.list(wrapper.setEntity(entity));
        return Result.build(ResultEnum.SELECT_SUCCESS, list);
    }

    @Operation(summary = "基础功能-条件查询分页", method = "GET")
    protected Result baseQueryPageByEntity(M entity, @PathVariable int pageIndex, @PathVariable int pageSize) {
        LambdaQueryWrapper<M> wrapper = new LambdaQueryWrapper<>();
        Page<M> MPage = new Page<>(pageIndex, pageSize);
        Page<M> page = service.page(MPage, wrapper.setEntity(entity));
        List<M> list = page.getRecords();
        return Result.build(ResultEnum.SELECT_SUCCESS, list).setMessage("查询成功，但没有查询条件生效，记录总条数为：" + page.getTotal() + "，已返回" + list.size() + "条");
    }

    @Operation(summary = "基础功能-新增", method = "POST")
    protected Result baseAdd(@RequestBody @Valid M entity) {
        boolean bool;
        try {
            bool = service.save(entity);
        } catch (RuntimeException e) {
            throw new ErrorException(e);
        }
        return bool ? Result.build(ResultEnum.INSERT_SUCCESS) : Result.build(ResultEnum.INSERT_FAIL);
    }

    @Operation(summary = "基础功能-修改", method = "PUT")
    protected Result baseEdit(@RequestBody @Valid M entity) {
        boolean bool;
        try {
            bool = service.updateById(entity);
        } catch (RuntimeException e) {
            throw new ErrorException(e);
        }
        return bool ? Result.build(ResultEnum.UPDATE_SUCCESS) : Result.build(ResultEnum.UPDATE_FAIL);
    }

    @Operation(summary = "基础功能-删除-路径参数单主键删除", method = "DELETE")
    protected Result baseDelById(@PathVariable("id") Integer id) {
        boolean bool;
        try {
            bool = service.removeById(id);
        } catch (RuntimeException e) {
            throw new ErrorException(e);
        }
        return bool ? Result.build(ResultEnum.DELETE_SUCCESS) : Result.build(ResultEnum.DELETE_FAIL);
    }

    /**
     * MybatisPlus不支持复合主键操作
     * @param m
     * @return
     */
    @Operation(summary = "基础功能-删除-路径参数复合主键删除", method = "DELETE")
    protected Result DelByEntity(@RequestBody M m){
        //TODO 复合主键删除的通用实现
        return null;
    }

    @Operation(summary = "基础功能-根据主键id批量删除(多个id根据,分隔)", method = "DELETE")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        String[] idsArr;
        try {
            idsArr = ids.split(StringPool.COMMA);
        } catch (RuntimeException e) {
            throw new ErrorException(e);
        }
        if (idsArr.length == 0) {
            return Result.build(ResultEnum.DELETE_FAIL).setMessage("删除条件id不能为空");
        }

        if (idsArr.length > 1000) {
            return Result.build(ResultEnum.DELETE_FAIL).setMessage("不能批量删除超过1000个数据");
        }
        List<Integer> idList;
        try {
            idList = Arrays.stream(idsArr).map(Integer::valueOf).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ErrorException(e);
        }
        return service.removeByIds(idList) ? Result.build(ResultEnum.DELETE_SUCCESS) : Result.build(ResultEnum.DELETE_FAIL);
    }
}

