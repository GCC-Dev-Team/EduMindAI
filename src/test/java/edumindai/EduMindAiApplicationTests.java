package edumindai;


import edumindai.utils.IflytekUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


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

//        IflytekUtil iflytekUtil = new IflytekUtil();
//
//        System.out.println(iflytekUtil.getWebsocketUrl());

//        BigDecimal a =new BigDecimal("10.00");
        BigDecimal b =new BigDecimal("0.00");
//        System.out.println(a.divide(b,4,BigDecimal.ROUND_HALF_UP));

        Integer a=0;

        System.out.println(a.equals(0));

        double v=0.0000;

        System.out.println(v);

        System.out.println(b.compareTo(BigDecimal.ZERO)==0);


        String a1="1,3,2";
        List<String> list = Arrays.asList(a1.split(","));

        System.out.println("来了"+list.contains("2"));
    }



}
