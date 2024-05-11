package edumindai.service.websocket;

import edumindai.model.entity.Answer;
import edumindai.model.entity.IflytekRoleContent;
import edumindai.model.entity.Question;
import edumindai.thread.IflytekThread;
import edumindai.utils.IflytekWebsocketServerUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
@Component
public class IflytekSocketServer extends TextWebSocketHandler {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 提供topic号给ai接口
     */

    private String topicId;
    /*
    聊天记录暂时保存在队列中
     */
    private List<Answer> iflytekRoleContentList;


    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
     */
    private static final CopyOnWriteArraySet<IflytekSocketServer> webSockets = new CopyOnWriteArraySet<IflytekSocketServer>();
    /**
     * 用来存在线连接用户信息(左边topic信息,右边session)
     */
    private static final ConcurrentHashMap<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();

    /**
     * socket 建立成功事件 @OnOpen
     *设置好session以及userId和topicId 如果topicId不为null,设置聊天信息
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        this.userId = IflytekWebsocketServerUtil.getUserId(session.getUri().getQuery());

        //topicId
        String parseTopicId = IflytekWebsocketServerUtil.getTopicId(session.getUri().getQuery());

        //首次连接,没有topicId
        if (parseTopicId == null){

            //生成一个topicId,保存到数据库

            //这个topicId保存当前对象

        }else {
            //topicId不为null,设置topicId
            this.topicId = parseTopicId;

            //聊天记录获取

        }

        webSockets.add(this);
        sessionPool.put(this.topicId, session);
    }

    /**
     * 接收消息事件 @OnMessage
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        System.out.println("信息长度"+message.getPayloadLength());
        String payload = message.getPayload();
        System.out.println("server 接收到发送的消息 " + payload);

        Question question = new Question();
        question.setTopicId(topicId);


        //运用多线程,组装问答内容给多线程去执行
        IflytekThread iflytekThread = new IflytekThread(question);

        //多线程开始
        iflytekThread.start();

        System.out.println("我是主线程");

        while (iflytekThread.isAlive()) {

            System.out.println("我进来了");
            try {
                Thread.sleep(1000);

                Queue<Answer> answers = IflytekClient.messageMap.get(topicId);

                System.out.println("我又");
                System.out.println("主线程地址" + answers);

                System.out.println(IflytekClient.messageMap + "主线程map地址");

                System.out.println(answers.size());

                //TODO 消息保存

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 服务端发送回去
        session.sendMessage(new TextMessage("server 发送消息 " + payload + " " + LocalDateTime.now()));
    }

    /**
     * socket 断开连接时 @OnClose
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        webSockets.remove(this);
        sessionPool.remove(session.getId());
        System.out.println("我断开了");
    }

}
