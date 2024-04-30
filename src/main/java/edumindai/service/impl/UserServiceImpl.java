package edumindai.service.impl;

import edumindai.common.Response;
import edumindai.enums.exception.LoginExceptionEnum;
import edumindai.exception.LoginServiceException;
import edumindai.mapper.UserMapper;
import edumindai.model.dto.LoginRequest;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.model.vo.LoginVO;
import edumindai.model.vo.RegisterVO;
import edumindai.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author ljz20
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-04-30 16:14:17
*/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Response<LoginVO> login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Response<RegisterVO> register(RegisterRequest registerRequest) {
        return null;
    }

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return user
     */
    @Override
    public User getUserByPhone(String phone) {

        try {

            return userMapper.findUserByPhone(phone);

        }catch (Exception e){

            throw new LoginServiceException(LoginExceptionEnum.QUERY_USER_ERROR);
        }

    }

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return user
     */
    @Override
    public User getUserByEmail(String email) {

        try {

            return userMapper.findUserByEmail(email);

        }catch (Exception e){

            throw new LoginServiceException(LoginExceptionEnum.QUERY_USER_ERROR);
        }

    }
}




