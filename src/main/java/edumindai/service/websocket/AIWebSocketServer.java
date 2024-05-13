package edumindai.service.websocket;


import edumindai.enums.IflytekRoleEnum;
import edumindai.exception.ServiceException;
import edumindai.model.entity.Answer;
import edumindai.model.entity.AnswerMessages;
import edumindai.model.entity.IflytekRoleContent;
import edumindai.model.entity.Question;
import edumindai.service.UserTopicAssociationService;
import edumindai.thread.IflytekThread;
import jakarta.websocket.*;

import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * AI问答websocket服务端(舍弃,使用IflytekSocketServer)
 */
@ServerEndpoint("/ws/ai/iflyteks")
@Component
@Slf4j
public class AIWebSocketServer {

    //mogodb负责消息记录的保存

    private static MongoTemplate mongoTemplate ;

    /***
     * Spring首先会创建并初始化mongoTemplate和userTopicAssociationService这两个bean,然后在进行依赖注入时，
     * 发现你在setMongoTemplate和setUserTopicAssociationService方法上使用了@Autowired注解，于是Spring就会调用这两个方法，
     * 把相应的bean赋值给mongoTemplate和userTopicAssociationService字段，
     * 这也就是为什么你会发现静态字段后面有值了。生命周期:先创建静态变量再创建bean的
     */
    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        AIWebSocketServer.mongoTemplate = mongoTemplate;
    }
    //负责topic的保存
    private static UserTopicAssociationService userTopicAssociationService;
    @Autowired
    public void setUserTopicAssociationService (UserTopicAssociationService userTopicAssociationService){

        AIWebSocketServer.userTopicAssociationService=userTopicAssociationService;
    };

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


    private Session session;

    private boolean insertTopicIntoSql = false;
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

        //TODO 鉴权:
        //获取userId和Token,然后校验比较
        this.userId= requestParameterMap.get("userId").get(0);


        //设置topicId 以及 session
        String parseTopicId = requestParameterMap.get("topicId").get(0);

        this.session = session;

        //没有topicId说明首次连接
        if (parseTopicId == "" || parseTopicId == null){

            //生成一个topicId topicId保存当前对象
            this.topicId = UUID.randomUUID().toString();

            this.answers = new ArrayList<>();

            this.insertTopicIntoSql = true;

        }else {

            //校验userId和topicId是否是对应
            boolean checked = checkUserIdAndTopicId(this.userId, this.topicId);

            if (checked) {
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

                //之前的信息发送给客户端
                sendMessageToUser(answer.toJson().toString());

            }

        }


        webSockets.add(this);
        sessionPool.put(this.topicId, session);

    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {



        //更新信息到文档 Mogodb
        AnswerMessages answerMessages = new AnswerMessages();
        answerMessages.setAnswers(answers);
        answerMessages.setTopicId(this.topicId);

        //说明有记录保存
        if (answers.size()>0){

            mongoTemplate.save(answerMessages);

            //进入条件:首先有聊天记录 2. 不是第一次连接
            //防止1.建立连接不问题然后断开,导致资源浪费
            //防止2.防止后续连接多次插入psql数据库
            if (insertTopicIntoSql){

                //保存到数据库(考虑一致性,所以要在关闭的时候选择保存)(新建连接保存,但是之后连接过了重新连接是不需要保存的)
                userTopicAssociationService.insertTopic(this.userId, this.topicId);
            }


        }

        webSockets.remove(this);
        sessionPool.remove(session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(Session session,String message) throws InterruptedException {

        //用户发送的提问信息保存在list中
        Answer answer = new Answer();
        answer.setRole(IflytekRoleEnum.User);
        answer.setContent(message);
        answer.setTokenSumCount(message.length());


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


    /**
     * 发送消息给自己
     * @param message
     */
    void sendMessageToUser(String message) {

        this.session.getAsyncRemote().sendText(message);
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