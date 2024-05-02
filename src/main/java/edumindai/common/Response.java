package edumindai.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果类
 * @param <T>
 */
@Data
@AllArgsConstructor
public class Response<T> implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public Response() {
    }

    /**
     * 成功
     */
    public Response success(int code, String message, T data){
        return new Response(code,message,data);
    }

    /**
     * 成功2.0
     * @param code 状态码
     * @param message 信息
     */
    public static Response success(int code,String message){
        return new Response(code,message,null);
    }


    /**
     * 错误信息
     * @param code 错误状态码
     * @param message 错误信息
     */
    public static Response error(int code,String message){
        return new Response(code,null,message);
    }

    
}