package edumindai.service.impl;


import edumindai.enums.exception.RegisterExceptionEnum;
import edumindai.exception.RegisterServiceException;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.service.RegisterStrategy;
import edumindai.service.VerificationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;




/**
 * 实现注册策略接口的方法,邮箱注册
 */
@Slf4j
@Component
public class EmailRegisterStrategy implements RegisterStrategy {

    @Resource
    VerificationService verificationService;


    @Override
    public User registerOperation(RegisterRequest registerRequest) {

        //验证验证码是否正确
        try {

            verificationService.verificationCodeCheck(registerRequest.getEmail(), registerRequest.getVerificationCode());

            return UserServiceImpl.createUser(registerRequest);

        }catch (Exception e){

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.VERIFY_CODE_ERROR);
        }

    }
}
