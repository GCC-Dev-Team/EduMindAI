package edumindai.service.impl;

import edumindai.mapper.UserTopicAssociationMapper;
import edumindai.model.entity.UserTopicAssociation;
import edumindai.service.UserTopicAssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ljz20
 * @description 针对表【user_topic_association】的数据库操作Service实现
 * @createDate 2024-05-12 01:27:43
 */
@Service
public class UserTopicAssociationServiceImpl implements UserTopicAssociationService {

    @Autowired
    private UserTopicAssociationMapper userTopicAssociationMapper;

    /**
     * 插入一个Topic关联
     *
     * @param userId  用户id
     * @param topicId topic id
     * @return true / false
     */
    @Override
    public boolean insertTopic(String userId, String topicId) {

        UserTopicAssociation userTopicAssociation = new UserTopicAssociation();

        // 保存到userTopicAssociation对象
        userTopicAssociation.setTopicId(topicId);

        userTopicAssociation.setId(topicId);

        userTopicAssociation.setTitle("New Connect");

        userTopicAssociation.setUserId(userId);

        return userTopicAssociationMapper.insertTopicByUserId(userTopicAssociation);
    }

    /**
     * 查询我的topic
     *
     * @param userId 用户id
     * @return List<UserTopicAssociation>
     */
    @Override
    public List<UserTopicAssociation> findMyTopic(String userId) {
        return userTopicAssociationMapper.findMyTopicByUserId(userId);

    }
}




