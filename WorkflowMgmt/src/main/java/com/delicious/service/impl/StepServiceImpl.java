package com.delicious.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.mapper.StepMapper;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import com.delicious.pojo.entity.Step;
import com.delicious.service.StepService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-06
 */
@Service
public class StepServiceImpl extends ServiceImpl<StepMapper, Step> implements StepService {
    @Resource
    private StepMapper stepMapper;

    @Override
    public Result AddSteps(String workflowCode, LocalDateTime workflowCreatTime, String steps) {
        JSONArray jsonArray = JSON.parseArray(steps);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Step step = Step.builder()
                    .stepName(jsonObject.getString("stepName"))
                    .stepOrder(i)
                    .stepState(0)//步骤状态：未完成
                    .workflowCode(workflowCode)
                    .build();
            if ("流程开始".equals(step.getStepName())){
                LocalDateTime nowTime = LocalDateTime.now();
                if (!nowTime.isBefore(workflowCreatTime)) {
                    step.setFinishTime(workflowCreatTime);
                    step.setStepState(1);
                }
            }
            int insert = stepMapper.insert(step);
            if (insert != 1) {
                return Result.build(ResultEnum.INSERT_FAIL);
            }
        }
        return Result.build(ResultEnum.INSERT_SUCCESS);
    }
}
