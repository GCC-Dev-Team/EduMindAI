package edumindai.model.entity;

import edumindai.enums.IflytekRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 大语言模型回答内容转化对象
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
     * 记录该消息是否为最后一条
     */
    private Integer status;
}
