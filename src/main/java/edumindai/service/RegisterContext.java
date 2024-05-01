package edumindai.service;

import edumindai.enums.RegisterPattern;
import edumindai.enums.exception.RegisterExceptionEnum;
import edumindai.exception.RegisterServiceException;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.service.impl.EmailRegisterStrategy;
import edumindai.service.impl.PhoneRegisterStrategy;


/**
 * 注册策略接口选择, 根据注册方式选择不同的策略,这个是选择不同策略然后包装
 */
public class RegisterContext {

    private final RegisterStrategy registerStrategy;

    public RegisterContext(RegisterPattern registerPattern)
    {
        switch (registerPattern){
            case Phone:

                registerStrategy = new PhoneRegisterStrategy();
                break;

            case Email:
                registerStrategy = new EmailRegisterStrategy();
                break;

            default:
                throw new RegisterServiceException(RegisterExceptionEnum.REGISTER_STRATEGY_TYPE_ERROR);
        }
    }

    /**
     * 注册操作
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    public User registerOperation(RegisterRequest registerRequest)
    {
        return registerStrategy.registerOperation(registerRequest);
    }
}
