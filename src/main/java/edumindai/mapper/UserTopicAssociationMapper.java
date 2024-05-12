package edumindai.mapper;


import edumindai.model.entity.UserTopicAssociation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Entity edumindai.model.entity.UserTopicAssociation
*/
public interface UserTopicAssociationMapper  {

    /**
     * topic新建,需要加入数据库
     * @param userTopicAssociation  topic信息
     * @return 是否插入成功
     */
    boolean insertTopicByUserId(UserTopicAssociation userTopicAssociation);

    /**
     * 根据userId查找我topic
     * @param userId 用户id
     * @return topic集合
     */
    List<UserTopicAssociation> findMyTopicByUserId(@Param("userId") String userId);
}




