package edumindai.mapper;


import edumindai.model.entity.Prompts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author ljz20
* @description 针对表【prompts】的数据库操作Mapper
* @createDate 2024-07-29 23:31:59
* @Entity edumindai.model.entity.Prompts
*/
@Mapper
public interface PromptsMapper{

    @Select("select id,prompt from prompts where id=#{id}")
    Prompts getPromptsById(Integer id);

}




