package edumindai;


import edumindai.utils.IflytekUtil;
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

        IflytekUtil iflytekUtil = new IflytekUtil();

        System.out.println(iflytekUtil.getWebsocketUrl());

    }



}
