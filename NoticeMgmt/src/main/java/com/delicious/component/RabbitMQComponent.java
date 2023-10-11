package com.delicious.component;

import com.alibaba.fastjson.JSON;
import com.delicious.pojo.entity.NoticeInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class RabbitMQComponent {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private WebSocket webSocket;

    public void sendMsg(NoticeInfo noticeInfo) {
        Message message = new Message(JSON.toJSONString(noticeInfo).getBytes());
        //给扇出交换机发消息
        rabbitTemplate.convertAndSend("exchange.fanout.notice", "any", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

    /**
     * 每当监听到自己消息队列中有需要广播的消息，就调用WebSocket的sendMessage方法
     */
    @RabbitListener(queues = {"queue.fanout.notice.8085"})
    public void receiveMsg(Message message) {
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("接受到的消息为：{}", msg);
        webSocket.sendMessage(msg);
    }
}
