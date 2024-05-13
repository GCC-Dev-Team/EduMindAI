package edumindai.service.websocket;

import com.google.gson.Gson;
import edumindai.enums.IflytekRoleEnum;
import edumindai.exception.ServiceException;
import edumindai.model.entity.*;
import edumindai.service.UserTopicAssociationService;
import edumindai.thread.IflytekThread;
import edumindai.utils.IflytekWebsocketServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 重大的bug,需要修复
 */
public class IflytekSocketServer extends TextWebSocketHandler {

    static int count = 0;

    //注入需要的bean对象
    //mogodb负责消息记录的保存

    private MongoTemplate mongoTemplate;
    //负责topic的保存

    private UserTopicAssociationService userTopicAssociationService;

    public IflytekSocketServer(UserTopicAssociationService userTopicAssociationService,MongoTemplate mongoTemplate) {
        this.mongoTemplate=mongoTemplate;
        this.userTopicAssociationService=userTopicAssociationService;
    }
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
    private List<Answer> answers;


    private WebSocketSession session;
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
     * 设置好session以及userId和topicId 如果topicId不为null,设置聊天信息
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        this.userId = IflytekWebsocketServerUtil.getUserId(session.getUri().getQuery());

        this.session = session;

        //topicId获取
        String parseTopicId = IflytekWebsocketServerUtil.getTopicId(session.getUri().getQuery());


        //首次连接,没有topicId
        if (parseTopicId == "" || parseTopicId == null) {

            //生成一个topicId topicId保存当前对象
            this.topicId = UUID.randomUUID().toString();

            this.answers = new ArrayList<>();

        } else {


            boolean checked = checkUserIdAndTopicId(this.userId, this.topicId);

            if (!checked) {
                throw new ServiceException("用户id和topicId不匹配", 1500);
            }

            //topicId不为null,设置topicId
            this.topicId = parseTopicId;

            //确保userId和TopicID是存在的并且mongodb有这个数据

            //聊天记录获取
            try {

                AnswerMessages answerMessages = mongoTemplate.findById(this.topicId, AnswerMessages.class);

                assert answerMessages != null;
                this.answers = answerMessages.getAnswers();
            } catch (Exception e) {

                e.printStackTrace();

                throw new ServiceException("聊天记录获取失败", 1500);
            }

            for (Answer answer : this.answers) {

                sendMessageToUser(answer.toJson().toString());

            }


        }

        webSockets.add(this);
        sessionPool.put(this.topicId, session);

        System.out.println("我是第"+count+"个客户端连接成功,topicId为"+this.topicId);

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
        System.out.println("信息长度" + message.getPayloadLength());
        String payload = message.getPayload();
        System.out.println("server 接收到发送的消息 " + payload);

        //用户发送的提问信息保存在list中
        Answer answer = new Answer();
        answer.setRole(IflytekRoleEnum.User);
        answer.setContent(payload);
        answer.setTokenSumCount(payload.length());


        this.answers.add(answer);


        //组织之前的聊天记录做到上下文 所有content的累计tokens需控制8192以内

        List<IflytekRoleContent> roleContentHistoryList = getRoleContentHistoryList(8192);


        Question question = new Question();
        question.setTopicId(this.topicId);
        question.setRoleContentList(roleContentHistoryList);


        //以下是回答内容(总结保存到Mogodb数据库的)
        //缺少内容以及总的token数量
        Answer respond = new Answer();
        respond.setRole(IflytekRoleEnum.Assistant);

        //使用string拼接,因为是流式,所以到时候拼接容易点(临时变量的)
        StringBuilder respondContentBuilder = new StringBuilder();

        System.out.println("我的topicID在while循环外面也就是提问"+this.topicId);


        //运用多线程,组装问答内容给多线程去执行
        IflytekThread iflytekThread = new IflytekThread(question);

        //多线程开始
        iflytekThread.start();

        //结束标识,退出循环,收到信息是2的结束
        boolean over = true;

        Thread.sleep(300);

        //能够退出循环的条件1.队列为null,2.子线程结束 !iflytekThread.isAlive()表示子程序结束(true是结束)
        while (over) {

            try {
                //线程先休息3s,等待子线程将数据装入
                Thread.sleep(300);

                //这个answers是队列的,是讯飞客户端那边的
                Queue<Answer> answers = IflytekClient.messageMap.get(this.topicId);

                System.out.println("我的topicID在while循环中"+this.topicId);


                //如果拿到队列为null,再次等待
                if (answers == null) {
                    continue;
                }
                //poll 出队操作,获取信息
                Answer poll = answers.poll();

                //TODO 优化,这个有可能会丢失数据
                if (poll == null){
                    continue;
                }

                //发送给用户(无论是不是最后一条信息都是发送给用户,因为是json格式)
                sendMessageToUser(poll.toJson().toString());

                //查看是不是最后的一条信息,1是过程中,流式传输,2是最后的一个信息,而且信息是没有的,只有token数量

                if (poll.getStatus()==2){

                    //设置token总数
                    respond.setTokenSumCount(poll.getTokenSumCount());

                    over = false;
                    break;
                }

                // 消息保存拼接
                String content = poll.getContent();

                respondContentBuilder.append(content);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //回答完成了,也得到了回答对象

        respond.setContent(respondContentBuilder.toString());

        //返回的消息保存到list中

        this.answers.add(respond);


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

        //更新信息到文档 Mogodb
        AnswerMessages answerMessages = new AnswerMessages();
        answerMessages.setAnswers(answers);
        answerMessages.setTopicId(this.topicId);

        //说明有记录保存
        if (answers.size()>0){

            mongoTemplate.save(answerMessages);

            //保存到数据库(考虑一致性,所以要在关闭的时候选择保存)
            userTopicAssociationService.insertTopic(this.userId, this.topicId);

        }
    }

    void sendMessageToUser(String message) {

        TextMessage textMessage = new TextMessage(message);

        try {
            this.session.sendMessage(textMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取历史信息(已经将问题添加进去了,聊天记录里面)
     *
     * @param maxToken 最大的token值
     * @return 提问的历史信息
     */
    private List<IflytekRoleContent> getRoleContentHistoryList(Integer maxToken) {

        //返回的信息
        List<IflytekRoleContent> roleContentHistoryList = new ArrayList<>();

        //将聊天信息转入 roleContentHistoryList 对象

        roleContentHistoryList.addAll(this.answers);

        //最后一条记录的token值(也就是这个历史记录的总token值),不超过最大的,直接返回,超过了就是要循环减去,知道不超过

        if (this.answers.get(answers.size() - 1).getTokenSumCount() <= maxToken) {

            return roleContentHistoryList;
        }

        //处理超过之后的,删除比较前面的聊天记录

        int sumToken = this.answers.get(answers.size() - 1).getTokenSumCount();

        for (Answer answer : this.answers) {

            roleContentHistoryList.removeFirst();

            if (sumToken - answer.getTokenSumCount() <= maxToken) {

                break;

            }

        }

        return roleContentHistoryList;
    }

    /**
     * 判断userId和TopicId是否一致
     * @param userId
     * @param topicId
     * @return
     */
    private boolean checkUserIdAndTopicId(String userId,String topicId){

        return userTopicAssociationService.checkUserIdAndTopicId(userId,topicId);
    }

}
