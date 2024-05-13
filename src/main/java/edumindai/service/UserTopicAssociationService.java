package edumindai.service;


import edumindai.model.entity.UserTopicAssociation;

import java.util.List;

/**
* @author ljz20
* @description 针对表【user_topic_association】的数据库操作Service
* @createDate 2024-05-12 01:27:43
*/
public interface UserTopicAssociationService  {

    //用户创建Topic
    boolean insertTopic(String userId, String topicId);

    //根据用户id查询Topic集合

    List<UserTopicAssociation> findMyTopic(String userId);

    boolean deleteOneByTopicId(String topicId);

    boolean checkUserIdAndTopicId(String userId,String topicId);


}
