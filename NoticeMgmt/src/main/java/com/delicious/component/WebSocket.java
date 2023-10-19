package com.delicious.component;

import com.delicious.exception.ErrorException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/webSocket/{clientName}")
public class WebSocket {
    //记录已建立了连接的客户端
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("clientName") String clientName) {
        sessionMap.put(clientName, session);
        log.info("【websocket消息】与客户端{" + clientName + "}建立了连接，当前连接数：" + sessionMap.size());
    }

    @OnClose
    public void onClose(@PathParam("clientName") String clientName) {
        sessionMap.remove(clientName);
        log.info("【websocket消息】与客户端{" + clientName + "}断开了连接，当前连接数：" + sessionMap.size());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("clientName") String clientName) {
        log.info("【websocket消息】客户端{" + clientName + "}发来了消息：" + message);
    }

    public void sendMessage(String message) {
        log.info("此次广播时已连接的客户端数量{}",sessionMap.size());
        sessionMap.forEach((name_key, session) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new ErrorException(e);
            }
        });
    }

}