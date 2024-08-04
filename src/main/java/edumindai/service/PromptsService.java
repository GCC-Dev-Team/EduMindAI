package edumindai.service;


import edumindai.model.entity.Prompts;

/**
* @author ljz20
* @description 针对表【prompts】的数据库操作Service
* @createDate 2024-07-29 23:31:59
*/
public interface PromptsService {

    String setQuestion(Integer promptId,String question);

}
