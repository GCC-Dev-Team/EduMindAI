package edumindai;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EduMindAiApplicationTests {

//    @Resource
//    MongoTemplate mongoTemplate;
//
//
//    @Autowired
//    UserTopicAssociationMapper userTopicAssociationMapper;

    @Test
    void contextLoads() throws Exception {

//        AnswerMessages answerMessages = mongoTemplate.findById("5afb00b1-c849-44ea-b313-69d8f96d4714", AnswerMessages.class);
//
//        mongoTemplate.remove(answerMessages);
//
//        System.out.println(answerMessages.getTopicId());
//        System.out.println(answerMessages.getAnswers().get(0).getContent());
//        System.out.println(mongoTemplate.findById("5afb00b1-c849-44ea-b313-69d8f96d4714", AnswerMessages.class));


        //注入需要的bean对象
        // 获取 Spring 上下文

        MongoTemplate bean = (MongoTemplate) SpringContextUtil.getBean(MongoTemplate.class);

        System.out.println(bean);

        // 获取 bean 对象
//    MyService myService = applicationContext.getBean("myService", MyService.class);
        //mogodb负责消息记录的保存



//        String data="Authorization=Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzZyIsInVzZXIiOnsiaWQiOiI2YWZlYzljNC02NmVmLTRkYzEtOTRlZC05OTQ0MWQzNDg0Y2IiLCJuaWNrbmFtZSI6bnVsbCwiYXZhdGFyIjpudWxsLCJ0eXBlcyI6Ik9SRElOQVJZIiwic3RhdHVzIjoiTk9STUFMIiwiZW1haWwiOiJsanpjb21lb25AZ21haWwuY29tIiwicGhvbmUiOm51bGwsImNyZWF0ZUF0IjoxNzE0NjM0ODQyNDkyLCJ1cGRhdGVBdCI6MTcxNDYzNDg0MjQ5MiwicGFzc3dvcmQiOiIkMmEkMTAkemg2aEJtZDZHM1dnTVpMOE5EUjZ1LnBEYmgzTUtvNDFISFpZMEhRR0Ivem5LMkprSklLWmEiLCJyZWdpc3RlclBhdHRlcm4iOiJFbWFpbCJ9LCJpYXQiOjE3MTQ2NDM2Nzh9.ylwe2Q1Fvnd05Ax5WmfaFBaFfliE4nmKk0muG3QG_Fw&topicId=&userId=";
//
//        String userId = IflytekWebsocketServerUtil.getUserId(data);
//        System.out.println(userId);


//        IflytekServiceImpl iflytekService = new IflytekServiceImpl();
//        iflytekService.sendQuestionAndAnswer(new Question());
       // IflytekUtil iflytekUtil = new IflytekUtil();
       // System.out.println(iflytekUtil.getWebsocketUrl());
//     /   String raw="{\"header\":{\"code\":0,\"message\":\"Success\",\"sid\":\"cht000bc837@dx18f5d4fad17b81a550\",\"status\":1},\"payload\":{\"choices\":{\"status\":1,\"seq\":6,\"text\":[{\"content\":\"首先，Java是一种同步的面向对象编程语言，它倾向于商业开发和团队合作，而Python则更倾向于数据分析。其次，Java版本\",\"role\":\"assistant\",\"index\":0}]}}}";
////
//        Gson gson =new Gson();
//
//        AnswerRaw answerRaw = gson.fromJson(raw, AnswerRaw.class);
//
//        AnswerRaw.Header header = answerRaw.getHeader();
//
//        System.out.println(header.getCode());
////
//        Answer answer = new Answer();
//
//        answer.setStatus(0);
//        answer.setContent("测试你好");
//        answer.setRole(IflytekRoleEnum.Assistant);
//
//
//        List<Answer> answers = new ArrayList<>();
//
//        answers.add(answer);
//
//        Answer answer2 = new Answer();
//
//        answer2.setStatus(1);
//        answer2.setContent("测试你好2");
//        answer2.setRole(IflytekRoleEnum.Assistant);
//
//
//        answers.add(answer2);
//
//        //出队操作
//
//        Queue<Answer> q = new LinkedList<>();
//
//        q.add(answer);
//        q.add(answer2);
//        System.out.println(q.size());
//        //这个peek是不会出队的,poll才会从队列删除
//        Answer poll = q.poll();
//        System.out.println(poll.getContent());
//
//        System.out.println(q.size());
//        System.out.println(q.isEmpty());

////
////
//        AnswerMessages answerMessages = new AnswerMessages();
//
//        answerMessages.setTopicId(UUID.randomUUID().toString());
//
//        answerMessages.setAnswers(answers);
//        mongoTemplate.save(answerMessages);
////
////        Query query = new Query();
//
//        //获取信息,根据id
//        //AnswerMessages byId = mongoTemplate.findById("70f85554-1b45-4a6c-b103-7e41e7da12bf", AnswerMessages.class);
////
//       // System.out.println(byId.getTopicId());
////
////        List<Answer> answers = byId.getAnswers();
//
//        List<IflytekRoleContent> roleContentList=new ArrayList<>();
//
//        boolean addAll = roleContentList.addAll(answers);
//
//        System.out.println(roleContentList.size());
//
//        roleContentList.removeFirst();
//
//        System.out.println(roleContentList.size());
//
//        System.out.println(roleContentList.getFirst().getContent());

        // System.out.println(answers.get(0));

//        System.out.println(answers.get(1).toJson());


//        mongoTemplate.find()

//        mongoTemplate
//
//        UserTopicAssociation userTopicAssociation = new UserTopicAssociation();
//
//        userTopicAssociation.setTopicId(UUID.randomUUID().toString());
//
//        userTopicAssociation.setId(UUID.randomUUID().toString());
//
//        userTopicAssociation.setTitle("小李");
//
//        userTopicAssociation.setUserId("6afec9c4-66ef-4dc1-94ed-99441d3484cb");
//
//        userTopicAssociationMapper.insertTopicByUserId(userTopicAssociation);

//        List<UserTopicAssociation> myTopicByUserId = userTopicAssociationMapper.findMyTopicByUserId("6afec9c4-66ef-4dc1-94ed-99441d3484cb");
//
//        System.out.println(myTopicByUserId.get(0).getUserId());

    }



}
