package edumindai.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
@Data
@Document(collection = "Message")
public class AnswerMessages {
    /**
     * topicId
     */
    @MongoId
    String topicId;

    /**
     * 聊天记录(跟AI的,同一个topicId的)
     */
    List<Answer> answers;
}
