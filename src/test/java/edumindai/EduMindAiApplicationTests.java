package edumindai;


import cn.hutool.crypto.asymmetric.RSA;
import com.google.gson.Gson;
import edumindai.enums.IflytekRoleEnum;
import edumindai.model.entity.Answer;
import edumindai.model.entity.AnswerMessages;
import edumindai.model.entity.AnswerRaw;
import edumindai.model.entity.Question;
import edumindai.service.impl.IflytekServiceImpl;
import edumindai.utils.IflytekUtil;
import edumindai.utils.IflytekWebsocketServerUtil;
import edumindai.utils.RSAUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EduMindAiApplicationTests {

    @Resource
    MongoTemplate mongoTemplate;



    @Test
    void contextLoads() throws Exception {

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
//
//        Answer answer = new Answer();
//
//        answer.setStatus(0);
//        answer.setContent("测试你好");
//        answer.setRole(IflytekRoleEnum.Assistant);
//
//        answer.setTokenCount(100);
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
//        answer2.setTokenCount(100);
//
//        answers.add(answer2);
//
//
//        AnswerMessages answerMessages = new AnswerMessages();
//
//        answerMessages.setTopicId(UUID.randomUUID().toString());
//
//        answerMessages.setAnswers(answers);
//        mongoTemplate.save(answerMessages);
//
//        Query query = new Query();

        //获取信息,根据id
        AnswerMessages byId = mongoTemplate.findById("cbfd4549-b8e8-42ef-b486-b8f17a3409a9", AnswerMessages.class);

        System.out.println(byId.getTopicId());

        List<Answer> answers = byId.getAnswers();

        System.out.println(answers.get(0).getContent());

        System.out.println(answers.get(1).getRole());
//        mongoTemplate.find()


    }



}
