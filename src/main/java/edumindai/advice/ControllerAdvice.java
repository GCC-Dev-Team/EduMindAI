package edumindai.advice;


import edumindai.common.Response;
import edumindai.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author huangfu
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    /**
     * serviceException 异常捕捉
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public Response requestException(ServiceException e){

        log.error(e.getMessage());

        return error(e);
    }
//

    /**
     * 处理自定义异常处理，将错误枚举传入当前封装结果集
     * @param e
     * @return
     */
    public Response error(ServiceException e){


        return Response.error(e.getErrorCode(), e.getMessage());
    }



    /**
     * 处理错误异常,非自定义异常是505
     * @param e
     * @return
     */

    public Response error(Exception e){

        return Response.error(505, e.getMessage());
    }
    @ExceptionHandler(value = Exception.class)
    public Response requestException(Exception e){

        log.error(e.getMessage());

        return error(e);
    }

}