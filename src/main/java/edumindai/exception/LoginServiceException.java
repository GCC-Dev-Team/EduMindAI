package edumindai.exception;

import edumindai.enums.exception.LoginExceptionEnum;

public class LoginServiceException extends ServiceException{

    public LoginServiceException(LoginExceptionEnum loginExceptionEnum) {
        super(loginExceptionEnum.getExceptionMessage(), loginExceptionEnum.getExceptionCode());
    }
}
