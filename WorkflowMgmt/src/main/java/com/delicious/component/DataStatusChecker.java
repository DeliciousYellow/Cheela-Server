package com.delicious.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.delicious.pojo.entity.workflow.WorkflowInfo;
import com.delicious.service.WorkflowInfoService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataStatusChecker {
    @Resource
    private RedisLock redisLock;
    @Resource
    private RabbitMQ_Workflow rabbitMQWorkflow;
    @Resource
    private WorkflowInfoService workflowInfoService;

    @Scheduled(fixedRate = 60000) // 定时每2分钟执行一次
    public void checkStatus() {
        if (redisLock.acquireLock("WorkflowState_Scheduled",59)){
            //如果获取到了锁
            LambdaQueryWrapper<WorkflowInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.ne(WorkflowInfo::getWorkflowState, "已完成");
            wrapper.ne(WorkflowInfo::getWorkflowState, "已过期");
            //查所有未完成的
            List<WorkflowInfo> list = workflowInfoService.list(wrapper);
            list.forEach((workflowInfo -> rabbitMQWorkflow.sendMsg(workflowInfo)));
            redisLock.releaseLock("WorkflowState_Scheduled");
        }
    }
}
