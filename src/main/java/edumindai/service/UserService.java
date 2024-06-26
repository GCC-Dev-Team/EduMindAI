package edumindai.service;


import edumindai.common.Response;
import edumindai.model.dto.LoginRequest;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.model.vo.LoginVO;
import edumindai.model.vo.RegisterVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author ljz20
* @description 针对表【users】的数据库操作Service
* @createDate 2024-04-30 16:14:17
*/
public interface UserService {

    /**
     * 登陆接口
     * @param loginRequest 登陆请求体
     */
    Response<LoginVO> login(@RequestBody LoginRequest loginRequest);

    /**
     * 注册接口
     * @param registerRequest 注册请求体
     */
    Response<RegisterVO> register(@RequestBody RegisterRequest registerRequest);

    /**
     * 获取用户通过手机号
     * @param phone 手机号
     * @return User 用户
     */
    User getUserByPhone(String phone);

    /**
     * 获取用户通过邮箱
     * @param email 邮箱
     * @return User 用户
     */
    User getUserByEmail(String email);


    /**
     * 获取我的topicId
     * @return
     */
    Response getTopics();
}
