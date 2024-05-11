package edumindai.utils;

/**
 * websocket服务端工具类
 * 用途:token检验,信息封装
 */
public class IflytekWebsocketServerUtil {

    /**
     * url的全部参数 获取token
     * @param urlQuery
     */
    public static String getToken(String urlQuery){

        //获取信息,根据url

        String[] split = urlQuery.split("&");

        String token = split[0].substring(21);

        return token;
    }

    /**
     * url的全部参数 获取userId
     * @param urlQuery
     * @return string
     */
    public static String getUserId(String urlQuery){

        //获取信息,根据url

        String[] split = urlQuery.split("&");

        String userId = split[1].substring(7);

        if (userId.equals("")||userId==null){

            throw new RuntimeException("userId解析失败");
        }
        return userId;
    }
    /**
     * url的全部参数 获取topicId
     * @param urlQuery
     * @return
     */
    public static String getTopicId(String urlQuery){

        //获取信息,根据url

        String[] split = urlQuery.split("&");

        String topicId = split[2].substring(8);

        return topicId;
    }
}
