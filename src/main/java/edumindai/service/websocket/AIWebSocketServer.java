package edumindai.service.websocket;


import com.google.gson.Gson;

import edumindai.model.entity.Answer;
import edumindai.model.entity.IflytekRoleContent;
import edumindai.model.entity.Question;
import edumindai.thread.IflytekThread;
import jakarta.websocket.*;

import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * AI问答websocket服务端(舍弃,使用IflytekSocketServer)
 */
@ServerEndpoint("/ws/ai/iflyteks")
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
     * 提供topic号给ai接口
     */

    private String topicId;

    private List<Answer> iflytekRoleContentList;

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
    @OnOpen
    public void onOpen(Session session) throws IOException {


        //获取信息,根据url
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();

        requestParameterMap.forEach((k, v) -> {
                    System.out.println(k + ":" + v);
                });

        //获取参数
        System.out.println(session.getQueryString());

        System.out.println("session地址"+session);

        //TODO 鉴权

        this.session = session;
        this.userId = "xiaoli";

        this.topicId= UUID.randomUUID().toString();



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
    public void onMessage(Session session,String message) {
        log.info("接收到UserID：{}的消息{}", userId, message);
        //TODO 采取json格式,传输给我我要转化成java对象

        Gson gson = new Gson();



        Question question = new Question();

        question.setTopicId(topicId);

       // question.setRoleContentList(iflytekRoleContentList);

        //TODO 消息上下文拼接 采取一些算法,因为发送问题需要保证8142token以下
//        IflytekRoleContent iflytekRoleContent = new IflytekRoleContent();
//        iflytekRoleContent.setContent("Java和python区别");
//        iflytekRoleContent.setRole(IflytekRoleEnum.User);

//        question.setType("chat");
//
//        question.setRoleContentList();

        //运用多线程,组装问答内容给多线程去执行
        IflytekThread iflytekThread = new IflytekThread(question);
        //多线程开始
        iflytekThread.start();

        System.out.println("我是主线程");

        while (iflytekThread.isAlive()){

            System.out.println("我进来了");
            try {
                Thread.sleep(1000);

                Queue<Answer> answers = IflytekClient.messageMap.get(topicId);

                System.out.println("我又");
                System.out.println("主线程地址"+answers);

                System.out.println(IflytekClient.messageMap+"主线程map地址");

                System.out.println(answers.size());

                //TODO 消息保存

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        session.getAsyncRemote().sendText("我已经收到");



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