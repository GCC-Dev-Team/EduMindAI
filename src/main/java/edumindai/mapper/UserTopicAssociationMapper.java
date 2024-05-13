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

    /**
     * 删除数据库数据,根据topicId,因为有的是只是连接了但是没有聊天记录,就是连接了就断开了
     * @param topicId
     * @return
     */
    boolean deleteOneByTopicId(@Param("topicId") String topicId);

    UserTopicAssociation findMyTopicByUserIdAndTopicId(@Param("userId") String userId,@Param("topicId") String topicId);
}




