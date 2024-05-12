package edumindai.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 讯飞返回的原始信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRaw {

    /**
     * 头部信息
     */
    Header header;
    /**
     * 讯飞返回的文本信息
     */
    Payload payload;


    @Data
    public class Header {
        int code;
        int status;
        String sid;
    }

    @Data
    public class Payload {
        Choices choices;

        //注意这个要在最后才有信息的
        Usage usage;

    }

    @Data
    public class Choices {
        List<Text> text;
    }

    @Data
    public class Text {
        String role;
        String content;
    }

    @Data
    public class Usage{

        UsageText text;

    }

    @Data
    public class UsageText{

        int prompt_tokens;
        int completion_tokens;
        int total_tokens;
    }

}
