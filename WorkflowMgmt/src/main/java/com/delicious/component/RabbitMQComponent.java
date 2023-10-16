package com.delicious.component;

import com.alibaba.fastjson.JSON;
import com.delicious.pojo.entity.NoticeInfo;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class RabbitMQComponent {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(NoticeInfo noticeInfo, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("noticeInfo",noticeInfo);
        map.put("message_type",type);
        Message message = new Message(JSON.toJSONString(map).getBytes());
        //给扇出交换机发消息
        rabbitTemplate.convertAndSend("exchange.fanout.notice", "any", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

    /**
     * 每当监听到自己消息队列中有需要广播的消息，就调用WebSocket的sendMessage方法
     */
    @RabbitListener(queues = {"queue.fanout.notice.8085"})
    public void receiveMsg(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            String msg = new String(body);
            log.info("接受到的消息为：{}", msg);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 处理错误情况，拒绝消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
