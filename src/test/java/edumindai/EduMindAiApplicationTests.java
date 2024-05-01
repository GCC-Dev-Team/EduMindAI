package edumindai;

import edumindai.mapper.UserMapper;
import edumindai.service.EmailService;
import edumindai.service.impl.UserServiceImpl;
import edumindai.utils.RedisCache;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class EduMindAiApplicationTests {

    @Resource
    UserServiceImpl userServiceImpl;

    @Resource
    UserMapper userMapper;

    @Resource
    RedisCache redisCache;

    @Resource
    EmailService emailService;



    @Test
    void contextLoads() {

//        System.out.println(RegexCheckUtil.isPhone("19835930193"));
//
//        User user = new User();
//        user.setId(UUID.randomUUID().toString());
//        user.setNickname("xiaoli");
//        user.setPassword("123456");
//        user.setPhone("19835930193");
//
//        user.setStatus(UserStatusEnum.NORMAL);
//
//        user.setTypes(UserTypeEnum.ADMIN);
//
//
//        userServiceImpl.saveUser(user);

        //设置邮箱验证码缓存

       // redisCache.setCacheObject("xiaoli@xiaodong", "278221",20, TimeUnit.SECONDS);


        if (redisCache.getCacheObject("xiaoli@xiaodong").equals("278221")){
            System.out.println("验证码正确");
        }else {
            System.out.println("历史就是");
        }

//        emailService.sendSimpleMail("2848762983@qq.com","测试","测试");




    }



}
