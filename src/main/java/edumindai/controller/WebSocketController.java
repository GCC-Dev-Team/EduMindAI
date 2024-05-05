package edumindai.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Stomp控制器,类似于RequestMapping注解的Spring MVC控制器
 */
@Controller
public class WebSocketController {

    @MessageMapping("/endpoint") // 定义消息映射路径
    @SendTo("/endpoint") // 定义消息发送目的地
    public String handleWebSocketMessage(String message) {
        // 处理接收到的消息，并返回响应
        return "Hello, " + message;
    }
}