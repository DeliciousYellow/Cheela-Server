package com.delicious.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delicious.pojo.entity.workflow.WorkflowInfo;
import com.delicious.pojo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-25
 */
public interface WorkflowInfoService extends IService<WorkflowInfo> {
    Result AddWorkflowInfoAndSteps(String resData);
}
