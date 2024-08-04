package edumindai.service.impl;


import edumindai.model.entity.Prompts;
import edumindai.service.PromptsService;
import edumindai.mapper.PromptsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author ljz20
* @description 针对表【prompts】的数据库操作Service实现
* @createDate 2024-07-29 23:31:59
*/
@Service
@RequiredArgsConstructor
public class PromptsServiceImpl implements PromptsService{
    final PromptsMapper promptsMapper;

    @Override
    public String setQuestion(Integer promptId, String question) {
        //获取prompt参数
        String prompt = promptsMapper.getPromptsById(promptId).getPrompt();

        prompt=prompt+"我现在的主题是:"+question;
        //根据枚举选择不同的处理方式(现在一条先简单拼接)
        return prompt;
    }
}




