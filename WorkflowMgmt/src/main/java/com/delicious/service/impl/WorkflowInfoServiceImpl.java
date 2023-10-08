package com.delicious.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.exception.ErrorException;
import com.delicious.mapper.WorkflowInfoMapper;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.WorkflowInfo;
import com.delicious.service.StepService;
import com.delicious.service.WorkflowInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-25
 */
@Service
public class WorkflowInfoServiceImpl extends ServiceImpl<WorkflowInfoMapper, WorkflowInfo> implements WorkflowInfoService {
    @Resource
    private WorkflowInfoMapper workflowInfoMapper;
    @Resource
    private StepService stepService;

    @Override
    public Result AddWorkflowInfoAndSteps(String resData) {
        JSONObject jsonObject = JSON.parseObject(resData);
        JSONObject workflowInfo_jsonObject = jsonObject.getJSONObject("workflowInfo");
        LocalDateTime workflowCreatTime = null;
        LocalDateTime workflowEndTime = null;
        try {
            workflowCreatTime = workflowInfo_jsonObject.getDate("workflowCreatTime").toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
            workflowEndTime = workflowInfo_jsonObject.getDate("workflowEndTime").toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        } catch (Exception e) {
            throw new ErrorException(e);
        }
        String workflowCode = UUID.randomUUID().toString();
        WorkflowInfo workflowInfo = WorkflowInfo.builder()
                .workflowCode(workflowCode)
                .workflowState(workflowInfo_jsonObject.getString("workflowState"))
                .workflowDepart(workflowInfo_jsonObject.getString("workflowDepart"))
                .workflowCreatTime(workflowCreatTime)
                .workflowEndTime(workflowEndTime)
                .workflowCreatorId(workflowInfo_jsonObject.getInteger("workflowCreatorId"))
                .workflowEmergencyDegree(workflowInfo_jsonObject.getString("workflowEmergencyDegree"))
                .workflowDetails(workflowInfo_jsonObject.getString("workflowDetails"))
                .build();
        int insert = workflowInfoMapper.insert(workflowInfo);
        if (insert != 1) {
            return Result.build(ResultEnum.INSERT_FAIL);
        }
        return stepService.AddSteps(workflowCode, workflowCreatTime, jsonObject.getJSONArray("steps").toString());
    }
}
