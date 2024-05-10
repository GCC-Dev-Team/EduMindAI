package edumindai.service.websocket;


import com.google.gson.Gson;
import edumindai.enums.IflytekRoleEnum;
import edumindai.model.entity.Answer;
import edumindai.model.entity.AnswerRaw;
import jakarta.websocket.*;


import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * 讯飞websocket客户端连接
 */
@ClientEndpoint
public class IflytekClient {


    /**
     * 用于json转化成java对象的
     */
    public static final Gson gson = new Gson();

    /**
     * websocket连接会话
     */

    Session session = null;

    /**
     * 这个topicId,用来保存不同对象的返回信息
     */
    private String topicId;
    /**
     * 用来结束线程阻塞等待标志位
     */
    public boolean isClose = false;
    /**
     * 消息队列,用于实现流式消息保存的
     */
    private Queue<Answer> messageQueue;
    //如何保存信息实现线程通信
    public static Map<String, Queue<Answer>> messageMap = new HashMap<>();

    /**
     * 构造参数,初始化客户端,准备连接
     *
     * @param endpointURI
     */
    public IflytekClient(URI endpointURI, String topicId) {
        try {
            session = ContainerProvider.getWebSocketContainer().connectToServer(this, endpointURI);

            this.topicId = topicId;
            /**
             * 用于本程序websocket服务端进行返回信息的提取
             */

            this.messageQueue = new LinkedList<>();

            messageMap.put(topicId, messageQueue);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //连接根据构造方法url连接后.构造方法还没走完
    @OnOpen
    public void open(Session session) {
        this.session=session;
    }

    //收到关闭
    @OnClose
    public void onClose() {
        System.out.println("Iflytek Client WebSocket is closing...");
    }


    /**
     * 讯飞回应的响应答案的地方
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) throws IOException {

        AnswerRaw answerRaw = gson.fromJson(message, AnswerRaw.class);
        if (answerRaw.getHeader().getCode() != 0) {
            System.out.println("发生错误，错误码为：" + answerRaw.getHeader().getCode());
            System.out.println("本次请求的sid为：" + answerRaw.getHeader().getCode());
            session.close();
        }
        List<AnswerRaw.Text> textList = answerRaw.getPayload().getChoices().getText();
        for (AnswerRaw.Text temp : textList) {

            Answer answer = new Answer();
            answer.setRole(IflytekRoleEnum.Assistant);
            answer.setContent(temp.getContent());
            answer.setStatus(0);

            messageQueue.add(answer);

        }
        if (answerRaw.getHeader().getStatus() == 2) {

            Answer answer = new Answer();
            answer.setRole(IflytekRoleEnum.Assistant);
            answer.setContent(null);
            answer.setStatus(2);

            messageQueue.add(answer);

            // 可以关闭连接，释放资源
            isClose = true;
            close();
        }
    }

    /**
     * 发送消息到wss服务
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 手动关闭连接
     */
    public void close() {
        try {
            if (session != null) {

                session.close();
                isClose = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
