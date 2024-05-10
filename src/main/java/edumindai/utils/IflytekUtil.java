package edumindai.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edumindai.config.IflytekConfig;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 讯飞接口工具类
 */
//@Component
public class IflytekUtil {


    private  String hostUrl = "https://spark-api.xf-yun.com/v3.1/chat";

    private  String appid = "f52d143b";

    private  String apiSecret = "YmUwY2IzMTkzMzBhYmRjYjM2ZThiMDIx";

    private  String apiKey = "0eed4b6468e4c5fb222b23ed6de3673e";


    /**
     * 获取websocket连接地址
     *
     * @return websocket连接地址
     * @throws NoSuchAlgorithmException 异常
     * @throws InvalidKeyException      无效异常
     * @throws MalformedURLException    异常
     */
    public String getWebsocketUrl() throws NoSuchAlgorithmException, InvalidKeyException, MalformedURLException {

        return getAuthUrl().replace("http://", "ws://").replace("https://", "wss://");
    }

    /**
     * 获取鉴权签名
     *
     * @return string https
     */
    public String getAuthUrl() throws NoSuchAlgorithmException, InvalidKeyException, MalformedURLException {

        URL url = new URL(hostUrl);

        // 获取当前时间并且转成时间戳
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        // 拼接 不要去动这里,\n删除就创建的websocket连接失败
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";

        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");

        mac.init(spec);


        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);


        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().

                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).

                addQueryParameter("date", date).addQueryParameter("host", url.getHost()).build();

        return httpUrl.toString();
    }

    /**
     * 获取发送消息的header
     *
     * @return JSONObject
     */
    public JSONObject getHeader() {

        JSONObject header = new JSONObject();  // header参数
        header.put("app_id", appid);
        header.put("uid", UUID.randomUUID().toString().substring(0, 10));

        return header;
    }

    /**
     * 获取websocket发送消息的header,自定义头部id
     *
     * @param headerId headerId
     * @return JSONObject
     */

    public JSONObject getHeader(String headerId) {

        JSONObject header = new JSONObject();  // header参数
        header.put("app_id", appid);
        header.put("uid", headerId);

        return header;
    }

    /**
     * 获取websocket发送消息的Parameter参数
     *
     * @return JSONObject
     */

    public JSONObject getParameter() {

        JSONObject parameter = new JSONObject(); // parameter参数
        JSONObject chat = new JSONObject();
        chat.put("domain", "generalv3");
        chat.put("temperature", 0.5);
        chat.put("max_tokens", 8192);
        parameter.put("chat", chat);

        return parameter;
    }

    /**
     * 获取websocket发送消息的Parameter参数
     *
     * @param maxTokens   回答最大token值
     * @param temperature 随机性[0,1]
     * @return JSONObject
     */

    public JSONObject getParameter(Integer maxTokens, Double temperature) {

        JSONObject parameter = new JSONObject(); // parameter参数
        JSONObject chat = new JSONObject();
        chat.put("domain", "generalv3");
        chat.put("temperature", temperature);
        chat.put("max_tokens", maxTokens);
        parameter.put("chat", chat);

        return parameter;
    }

    /**
     * 获取websocket发送消息的Payload参数
     *
     * @param text 问题集合(Json集合)
     * @return JSONObject
     */

    public JSONObject getPayload(JSONArray text) {

        JSONObject payload = new JSONObject(); // payload参数

        JSONObject message = new JSONObject();

        message.put("text", text);

        payload.put("message", message);

        return payload;
    }
}
