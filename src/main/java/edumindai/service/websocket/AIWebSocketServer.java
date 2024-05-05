package edumindai.service.websocket;


import com.google.gson.Gson;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * AI问答websocket服务端
 */
@ServerEndpoint("/ws/test/{userId}/{topicId}")
@Component
@Slf4j

public class AIWebSocketServer {
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
     */
    private static final CopyOnWriteArraySet<AIWebSocketServer> webSockets = new CopyOnWriteArraySet<AIWebSocketServer>();
    /**
     * 用来存在线连接用户信息
     */
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     */

    private static int accunt = 0;
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {

        System.out.println("session地址"+session);
        this.session = session;
        this.userId = userId;

        System.out.println("ws地址"+webSockets);

        accunt++;
        System.out.println(accunt);
        webSockets.add(this);
        sessionPool.put(userId, session);
        log.info("建立与UserID：{}的消息提醒计数连接", userId);
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
         webSockets.remove(this);
        sessionPool.remove(this.userId);
        log.info("关闭与UserID：{}的消息提醒计数连接", userId);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("接收到UserID：{}的消息{}", userId, message);
        Gson gson = new Gson();
//        Message message1 = gson.fromJson(message, Message.class);
//
//        sendOneMessage(message1.getToId(), message1.getContext());
    }

    /**
     * 发送错误时的处理
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发送到UserID：{}的消息传输失败", userId, error);
    }

    /**
     * 广播消息
     *
     * @param message
     */
    public void sendAllMessage(String message) {
        for (AIWebSocketServer socketServer : webSockets) {
            if (socketServer.session.isOpen()) {
                socketServer.session.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 单人单播消息
     *
     * @param userId
     * @param message
     */
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 多人单播消息
     *
     * @param userIds
     * @param message
     */
    public void sendMoreMessage(Integer[] userIds, String message) {
        for (Integer userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }

    }
}