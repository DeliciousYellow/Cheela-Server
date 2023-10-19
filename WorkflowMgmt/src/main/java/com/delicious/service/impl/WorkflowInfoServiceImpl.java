package com.delicious.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.pojo.entity.workflow.WorkflowInfo;
import com.delicious.exception.ErrorException;
import com.delicious.mapper.WorkflowInfoMapper;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
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
        LocalDateTime workflowStartTime = null;
        LocalDateTime workflowEndTime = null;
        try {
            workflowStartTime = workflowInfo_jsonObject.getDate("workflowStartTime").toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
            workflowEndTime = workflowInfo_jsonObject.getDate("workflowEndTime").toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        } catch (Exception e) {
            throw new ErrorException(e);
        }
        //判断当前时间工作流的状态应该是什么
        LocalDateTime nowTime = LocalDateTime.now();
        String workflowState = null;
        if (nowTime.isBefore(workflowStartTime)) {
            workflowState = "未开始";
        } else if (nowTime.isAfter(workflowStartTime) && nowTime.isBefore(workflowEndTime)) {
            //在工作流的有效时间内
            workflowState = "进行中";
        } else {
            //已经超过最晚时间
            workflowState = "已过期";
        }
        String workflowCode = UUID.randomUUID().toString();
        WorkflowInfo workflowInfo = WorkflowInfo.builder()
                .workflowCode(workflowCode)
                .workflowState(workflowState)
                .workflowDepart(workflowInfo_jsonObject.getString("workflowDepart"))
                .workflowStartTime(workflowStartTime)
                .workflowEndTime(workflowEndTime)
                .workflowCreatorId(workflowInfo_jsonObject.getInteger("workflowCreatorId"))
                .workflowEmergencyDegree(workflowInfo_jsonObject.getString("workflowEmergencyDegree"))
                .workflowDetails(workflowInfo_jsonObject.getString("workflowDetails"))
                .build();
        int insert = workflowInfoMapper.insert(workflowInfo);
        if (insert != 1) {
            return Result.build(ResultEnum.INSERT_FAIL);
        }
        return stepService.AddSteps(workflowCode, workflowStartTime, jsonObject.getJSONArray("steps").toString());
    }
}
