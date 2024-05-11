package edumindai.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 大语言模型的问答请求类(websocket客户端使用)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    /**
     * topicId 用于wss客户端返回信息,根据这个获取信息
     */
    private String topicId;

    /**
     * 问答内容
     */
    private List<IflytekRoleContent> roleContentList;

}
