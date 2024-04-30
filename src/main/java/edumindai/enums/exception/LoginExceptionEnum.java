package edumindai.enums.exception;

public enum LoginExceptionEnum {
    LOGIN_PARAMETER_ERROR("用户名并非邮箱或者手机号",1001),
    QUERY_USER_ERROR("查询用户异常",1000),
    SQL_INSERT_ERROR("注册用户异常",1002);


    private String exceptionMessage;

    private Integer exceptionCode;

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
