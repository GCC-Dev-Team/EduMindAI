package edumindai.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edumindai.enums.IflytekRoleEnum;
import edumindai.model.entity.Answer;
import edumindai.model.entity.IflytekRoleContent;
import edumindai.model.entity.Question;
import edumindai.service.AIService;
import edumindai.service.websocket.IflytekClient;
import edumindai.utils.IflytekUtil;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 讯飞AI实现类,接入讯飞星火AI大模型
 */
//@Service
public class IflytekServiceImpl implements AIService {

    IflytekUtil iflytekUtil = new IflytekUtil();

    /**
     * 连接websocket
     *
     * @return 连接端
     */
    public IflytekClient client(String topicId) throws InterruptedException {
        //获取websocket鉴权地址
        String url = null;
        try {

            url = iflytekUtil.getWebsocketUrl();

        } catch (Exception e) {
            //获取url异常
            throw new RuntimeException(e);
        }

        //连接
        IflytekClient iflytekClient = null;
        try {
            iflytekClient = new IflytekClient(new URI(url), topicId);
        } catch (URISyntaxException e) {
            //连接异常
            throw new RuntimeException(e);
        }

        return iflytekClient;


    }

    /**
     * 连接讯飞websocket,发送问答请求信息
     *
     * @param question 问题
     */
    @Override
    public void sendQuestionAndAnswer(Question question) {

        //建立websocket连接

        IflytekClient iflytekClient = null;

        try {

            iflytekClient = client(question.getTopicId());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //提问部分
        //最终提交给讯飞请求信息的text注意因为要上下文,所以是数组,还要是json格式(IflytekRoleContent对象的)
        JSONArray iflytekRoleContentArray = new JSONArray();

        List<IflytekRoleContent> roleContentList = question.getRoleContentList();

        roleContentList.forEach(iflytekRoleContent -> {

            iflytekRoleContentArray.add(iflytekRoleContent.toJson());
        });


        //构建请求信息(最终请求信息header parameter payload集在一起)
        JSONObject requestJson = new JSONObject();

        //请求参数(header parameter payload)
        requestJson.put("header", iflytekUtil.getHeader());
        requestJson.put("parameter", iflytekUtil.getParameter());
        requestJson.put("payload", iflytekUtil.getPayload(iflytekRoleContentArray));

        //发送问题
        iflytekClient.sendMessage(requestJson.toString());

        while (!iflytekClient.isClose) {

            try {

                Thread.sleep(200);

            } catch (Exception e) {

                throw new RuntimeException(e);
            }

        }
    }


}
