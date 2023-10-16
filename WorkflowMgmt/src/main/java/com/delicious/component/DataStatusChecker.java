package com.delicious.component;

import com.delicious.service.WorkflowInfoService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataStatusChecker {

    @Resource
    private WorkflowInfoService workflowInfoService;
    @Scheduled(fixedRate = 60000) // 定时每分钟执行一次
    public void checkStatus() {

    }
}
