package edumindai.utils;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;

import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
    //TODO 更换记得修改
    public static String ACCESS_KEY_ID = "QWcbtBYveDeiofkkJjjsFNA8XrJnhZbJrMZsSVA37r5WaETyc";
    private static String ACCESS_KEY_SECRET = "BGrGxTYGnqx6Nktxs1krmBEetRsvh1";


    //pub_verif_register_ttl
    boolean sendMessageToUserByRegister(String phoneNumber,String code){
        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", code);
        templateData.put("ttl","5");

        return sendMessageByTemplateData(templateData,phoneNumber,"","pub_verif_register_ttl");
    }

    boolean sendMessageByTemplateData(Map<String, String> templateData,String phoneNumber,String signature,String templateId){

        // 初始化
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET); // 若使用简易验签模式仅传入第一个参数即可
        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(phoneNumber)
                .setSignature(signature)
                .setTemplateId(templateId)
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);

            return false;
        }

        return true;
    }
}
