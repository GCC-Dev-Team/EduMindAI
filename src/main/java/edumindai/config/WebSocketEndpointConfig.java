package edumindai.config;


import edumindai.filter.WebSocketAuthInterceptor;
import edumindai.service.UserTopicAssociationService;
import edumindai.service.websocket.IflytekSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketEndpointConfig implements WebSocketConfigurer {

    @Autowired
    WebSocketAuthInterceptor webSocketAuthInterceptor;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    UserTopicAssociationService userTopicAssociationService;
    /**
     * 注入ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的WebSocket Endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /** 不使用Endpoint
     * 不使用ServerEndpointExporter的方式,直接使用封装的springboot提供的WebSocketHandlerRegistry
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(new IflytekSocketServer(userTopicAssociationService,mongoTemplate), "/ws/ai/iflytek/delete")
                .addInterceptors(webSocketAuthInterceptor)
                .setAllowedOrigins("*");
    }
}

