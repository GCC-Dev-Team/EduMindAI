package edumindai.enums.exception;

public enum LoginExceptionEnum {
    LOGIN_STRATEGY_TYPE_ERROR("登陆类型错误",2001),
    PASSWORD_ERROR("密码错误",2002);

    private final String exceptionMessage;

    private final Integer exceptionCode;

    LoginExceptionEnum(String exceptionMessage, Integer exceptionCode) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public Integer getExceptionCode() {
        return exceptionCode;
    }
}
