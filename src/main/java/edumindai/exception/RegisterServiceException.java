package edumindai.exception;


import edumindai.enums.exception.RegisterExceptionEnum;

/**
 * 注册异常
 */
public class RegisterServiceException extends ServiceException{

    public RegisterServiceException(RegisterExceptionEnum registerExceptionEnum) {

        super(registerExceptionEnum.getExceptionMessage(), registerExceptionEnum.getExceptionCode());
    }
}
