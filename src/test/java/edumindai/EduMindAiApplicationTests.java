package edumindai;

import edumindai.enums.db.UserStatusEnum;
import edumindai.enums.db.UserTypeEnum;
import edumindai.mapper.UserMapper;
import edumindai.model.entity.User;
import edumindai.service.EmailService;
import edumindai.service.impl.UserServiceImpl;
import edumindai.service.impl.security.EmailLoginUserDetailsImpl;
import edumindai.utils.JwtUtil;
import edumindai.utils.RedisCache;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setNickname("xiaoli");
        user.setPassword("123456");
        user.setPhone("19835930193");

        user.setStatus(UserStatusEnum.NORMAL);

        user.setTypes(UserTypeEnum.ADMIN);
//
//
//        userServiceImpl.saveUser(user);

        //设置邮箱验证码缓存

       // redisCache.setCacheObject("xiaoli@xiaodong", "278221",20, TimeUnit.SECONDS);

//
//        Map<String, Object> claims = new HashMap<>();
//
//        // 设置User对象的属性到claims中
//        claims.put("user", null);
        //emailService.sendSimpleMail("2848762983@qq.com","测试","测试");


        //EmailLoginUserDetailsImpl emailLoginUserDetails = new EmailLoginUserDetailsImpl(user);

        String s = JwtUtil.generateJwtToken(user);

        System.out.println(s);

        User userDetailsFromToken = (User)JwtUtil.getUserFromToken(s);

        System.out.println(userDetailsFromToken.getTypes());


    }



}
