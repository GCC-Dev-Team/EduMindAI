package edumindai.model.entity;

import edumindai.enums.IflytekRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 大语言模型回答内容转化对象(返回给用户wss的内容)
 */



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    /*
    * 回答的角色
     */
    private IflytekRoleEnum role;
    /**
     * 回答的内容
     */
    private String content;

    /**
     * 记录该消息是否为最后一条(0 不是最后一条,1是最后一条)
     */
    private Integer status;

    /**
     * 该消息的token
     */
    private Integer tokenCount;

}
