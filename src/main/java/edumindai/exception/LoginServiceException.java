package edumindai.exception;


import edumindai.enums.exception.LoginExceptionEnum;

/**
 * 登陆异常
 */
public class LoginServiceException extends ServiceException{

    public LoginServiceException(LoginExceptionEnum loginExceptionEnum) {

        super(loginExceptionEnum.getExceptionMessage(), loginExceptionEnum.getExceptionCode());
    }
}
