package edumindai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 舍弃,不适用配置注入的消息
 */
@Getter
@Setter
@Configuration
public class IflytekConfig {

    @Value("${iflytek.hostUrl}")
    private  String hostUrl = "https://spark-api.xf-yun.com/v3.1/chat";

    @Value("${iflytek.appid}")
    private  String appid = "f52d143b";

    @Value("${iflytek.apiSecret}")
    private  String apiSecret = "YmUwY2IzMTkzMzBhYmRj";

    @Value("${iflytek.apiKey}")
    private  String apiKey = "0eed4b6468e4c5fb222b23ed6de367";

    @Value("${iflytek.domain}")
    private String domain;


}