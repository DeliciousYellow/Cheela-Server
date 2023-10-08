package com.delicious.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Step;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-06
 */
public interface StepService extends IService<Step> {
    Result AddSteps(String workflowCode, LocalDateTime workflowCreatTime, String steps);
}
