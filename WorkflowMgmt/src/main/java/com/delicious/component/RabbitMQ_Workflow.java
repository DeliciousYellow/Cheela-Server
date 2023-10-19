package com.delicious.component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.delicious.pojo.entity.workflow.Step;
import com.delicious.pojo.entity.workflow.WorkflowInfo;
import com.delicious.exception.ErrorException;
import com.delicious.service.StepService;
import com.delicious.service.WorkflowInfoService;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class RabbitMQ_Workflow {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private WorkflowInfoService workflowInfoService;
    @Resource
    private StepService stepService;

    public void sendMsg(WorkflowInfo workflowInfo) {
        Message message = new Message(JSON.toJSONString(workflowInfo).getBytes());
        rabbitTemplate.convertAndSend("exchange.direct.workflow", "routing.workflow", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

    @RabbitListener(queues = {"queue.direct.workflow"})
    public void receiveMsg(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            String msg = new String(body);
            log.info("接受到的消息为：{}", msg);
            //根据消息队列中的workflowInfo修改workflowState
            WorkflowInfo workflowInfo = JSON.parseObject(msg, WorkflowInfo.class);
            LocalDateTime startTime = workflowInfo.getWorkflowStartTime();
            LocalDateTime endTime = workflowInfo.getWorkflowEndTime();
            // 获取当前时间
            LocalDateTime nowTime = LocalDateTime.now();
            if ("已完成".equals(workflowInfo.getWorkflowState())){
                //如果工作流已完成，不做处理
            } else if (nowTime.isBefore(startTime)) {
                //还未开始
                workflowInfo.setWorkflowState("未开始");
            } else if (nowTime.isAfter(startTime)&&nowTime.isBefore(endTime)) {
                //在工作流的有效时间内
                workflowInfo.setWorkflowState("进行中");
                try{
                    //除了设置进行中还要把对应的第一步,流程开始修改为绿色
                    LambdaQueryWrapper<Step> wrapper = new LambdaQueryWrapper<>();
                    //当前工作流对应的步骤
                    wrapper.eq(Step::getWorkflowCode,workflowInfo.getWorkflowCode());
                    //对应步骤中的第一个步骤
                    wrapper.eq(Step::getStepOrder,0);
                    Step step = stepService.list(wrapper).get(0);
                    stepService.updateById(step.setStepState(1).setFinishTime(LocalDateTime.now()));
                }catch (Exception e){
                    throw new ErrorException(e);
                }
            } else if (nowTime.isAfter(endTime)){
                //已经超过最晚时间
                workflowInfo.setWorkflowState("已过期");
            }
            if (workflowInfoService.updateById(workflowInfo)) {
                // 手动确认消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }else {
                // 拒绝消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            }
        } catch (Exception e) {
            // 拒绝消息，可以不用重新加入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
