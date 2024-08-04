package edumindai.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBO {
    // 问题
    private String question;

    // 提示ID
    private Integer promptId;

    // 自定义prompt参数
    private String prompt;

    // 状态，0是需要参数，1是不需要prompt参数
    private int status;
}
