package edumindai;


import cn.hutool.crypto.asymmetric.RSA;
import com.google.gson.Gson;
import edumindai.model.entity.AnswerRaw;
import edumindai.model.entity.Question;
import edumindai.service.impl.IflytekServiceImpl;
import edumindai.utils.IflytekUtil;
import edumindai.utils.RSAUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EduMindAiApplicationTests {



    @Test
    void contextLoads() throws Exception {


//        IflytekServiceImpl iflytekService = new IflytekServiceImpl();
//        iflytekService.sendQuestionAndAnswer(new Question());
        IflytekUtil iflytekUtil = new IflytekUtil();
        System.out.println(iflytekUtil.getWebsocketUrl());
//        String raw="{\"header\":{\"code\":0,\"message\":\"Success\",\"sid\":\"cht000bc837@dx18f5d4fad17b81a550\",\"status\":1},\"payload\":{\"choices\":{\"status\":1,\"seq\":6,\"text\":[{\"content\":\"首先，Java是一种同步的面向对象编程语言，它倾向于商业开发和团队合作，而Python则更倾向于数据分析。其次，Java版本\",\"role\":\"assistant\",\"index\":0}]}}}";
////
//        Gson gson =new Gson();
//
//        AnswerRaw answerRaw = gson.fromJson(raw, AnswerRaw.class);
//
//        AnswerRaw.Header header = answerRaw.getHeader();
//
//        System.out.println(header.getCode());


    }



}
