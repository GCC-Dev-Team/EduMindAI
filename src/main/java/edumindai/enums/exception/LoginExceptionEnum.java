package edumindai.enums.exception;

public enum LoginExceptionEnum {
    WX_CODE_ERROR("微信code错误",1001),
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
