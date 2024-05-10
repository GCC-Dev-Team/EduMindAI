package edumindai.service;


import edumindai.model.entity.Question;

/**
 * V 1.0 讯飞大语言模型
 */
public interface AIService {

    /**
     * 发送问题,回答问题
     * @param question 问题
     */
    void sendQuestionAndAnswer(Question question);
}
