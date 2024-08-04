package edumindai.service.impl;

import edumindai.common.Response;
import edumindai.enums.exception.RegisterExceptionEnum;
import edumindai.exception.RegisterServiceException;
import edumindai.service.EmailService;
import edumindai.service.VerificationService;
import edumindai.utils.RedisCache;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务接口,包括验证码生成和验证码发送以及验证码保存
 */
@Component
public class VerificationServiceImpl implements VerificationService {

    private static final Logger log = LoggerFactory.getLogger(VerificationServiceImpl.class);
    @Resource
    private EmailService emailService;

    @Resource
    private RedisCache redisCache;

    //设置验证码过期时间 5分钟
    private static final int VERIFY_CODE_EXPIRED_TIME = 60 * 5;

    @Override
    public Response verificationCode(String phoneNumber, String email) {

        //验证码生成
        String verifyCode = generateVarifyCode();

        String verifyCodeContent ="你的验证码是"+verifyCode;

        //验证码保存
        //发送验证码

        if (phoneNumber != null) {

            //这个手机号有没有注册

            redisCache.setCacheObject(phoneNumber, verifyCode, VERIFY_CODE_EXPIRED_TIME, TimeUnit.SECONDS);

            //TODO 手机验证码发送功能,所以直接返回先,后续发送给手机


            return Response.success(1, "发送成功"+verifyCode);

        } else if (email != null) {

            //这个邮箱有没有注册(需要判断)

            redisCache.setCacheObject(email, verifyCode, VERIFY_CODE_EXPIRED_TIME, TimeUnit.SECONDS);

            //发送验证码
            emailService.sendSimpleMail(email, "验证码", verifyCodeContent);


            return Response.success(1, "发送成功");
        }

        throw new RegisterServiceException(RegisterExceptionEnum.VERIFY_QUERY_ERROR);
    }

    //检验验证码,主要是redis检查

    /**
     * 检验验证码
     *
     * @param key              手机号或者邮箱
     * @param verificationCode 验证码
     */
    @Override
    public void verificationCodeCheck(String key, String verificationCode) throws Exception {
        boolean verifyBool = false;
        try {

            verifyBool = redisCache.getCacheObject(key).equals(verificationCode);

        }catch (Exception e){
            //查询不到这个手机号的异常处理

            log.error(e.getMessage());

            throw new Exception("验证码错误,没有找到手机号或邮箱");

        }
        //查询通过了,可以执行删除并且返回业务层说验证通过

        if (verifyBool) {



            return;
        }

        throw new Exception("验证码校验错误");
    }

    /**
     * 删除验证码
     * @param key 手机号/邮箱
     * @return
     */
    @Override
    public boolean verificationDelete(String key) {

        boolean delete ;

        try {
            //删除成功的
            delete = redisCache.deleteObject(key);

        }catch (Exception e){

            //删除失败的,可能键值对为null
            e.printStackTrace();

            delete = false;

        }

        return delete;
    }

    /**
     * 生成六位数字的验证码
     *
     * @return string
     */
    private static String generateVarifyCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int digit;
            if (i == 0) { // 第一位数字可以是0
                digit = random.nextInt(10);
            } else {
                digit = random.nextInt(9) + 1; // 除了第一位数字外，其他位数字不能是0
            }
            sb.append(digit);
        }

        return sb.toString();
    }
}
