package edumindai.exception;

public class ServiceException extends RuntimeException {

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    private Integer errorCode;



    public ServiceException() {

    }


    public ServiceException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
