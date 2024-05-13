package edumindai.model.entity;

import edumindai.enums.IflytekRoleEnum;
import jakarta.persistence.DiscriminatorValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 大语言模型回答内容转化对象(返回给用户wss的内容)
 * 流式子返回给用户的信息和保存的信息,tokenCount是token的总数(包括累计的历史信息),但是这个属性只会用于保存到mongodb中才会用到
 * 返回类是不含有tokenCount属性
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Answer extends IflytekRoleContent {

    /**
     * 记录该消息是否为最后一条(0 不是最后一条,1是最后一条)主要是wss讯飞客户端流式使用
     */
    private Integer status;

    /**
     * 该消息的总token(注意:上面有历史信息,会累计的)
     */
    private Integer tokenSumCount;

}
