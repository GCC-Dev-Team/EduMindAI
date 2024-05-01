package edumindai.enums.exception;

public enum RegisterExceptionEnum {
    LOGIN_PARAMETER_ERROR("用户名并非邮箱或者手机号",1000),
    QUERY_USER_ERROR("查询用户异常",1001),
    SQL_INSERT_ERROR("注册用户异常",1002),
    VERIFY_QUERY_ERROR("验证码请求参数错误",1003),
    REGISTER_STRATEGY_TYPE_ERROR("注册类型错误",1004),
    VERIFY_CODE_ERROR("验证码错误",1005);


    private final String exceptionMessage;

    private final Integer exceptionCode;

    RegisterExceptionEnum(String exceptionMessage, Integer exceptionCode) {
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
