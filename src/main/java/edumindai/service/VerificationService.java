package edumindai.service;

import edumindai.common.Response;

/**
 * 验证接口
 * 验证码生成以及发送
 * 验证码校验
 */
public interface VerificationService {

    /**
     * 发送验证码
     * @param phoneNumber 手机号
     * @param email 邮箱
     *              可选字段
     * @return 发送成功告诉给用户
     */
    Response verificationCode(String phoneNumber, String email);

    //验证码校验

    /**
     * 验证码校验
     *
     * @param key              手机号或者邮箱
     * @param verificationCode 验证码
     */
     void verificationCodeCheck(String key, String verificationCode) throws Exception;

    /**
     * 验证码删除
     * @param key
     * @return
     */
     boolean verificationDelete(String key);
}
