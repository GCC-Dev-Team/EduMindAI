package edumindai.exception;

import edumindai.enums.exception.LoginExceptionEnum;

/**
 * 登陆异常
 */
public class LoginServiceException extends ServiceException{

    /**
     * 登陆异常
     * @param loginExceptionEnum 登陆枚举
     */
    public LoginServiceException(LoginExceptionEnum loginExceptionEnum) {
        super(loginExceptionEnum.getExceptionMessage(), loginExceptionEnum.getExceptionCode());
    }
}
