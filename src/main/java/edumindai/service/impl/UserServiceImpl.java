package edumindai.service.impl;

import edumindai.common.Response;
import edumindai.enums.db.UserStatusEnum;
import edumindai.enums.db.UserTypeEnum;
import edumindai.enums.exception.LoginExceptionEnum;
import edumindai.enums.exception.RegisterExceptionEnum;
import edumindai.exception.LoginServiceException;
import edumindai.exception.RegisterServiceException;
import edumindai.mapper.UserMapper;
import edumindai.model.dto.LoginRequest;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.model.entity.UserTopicAssociation;
import edumindai.model.vo.LoginVO;
import edumindai.model.vo.RegisterVO;
import edumindai.model.vo.TopicsVO;
import edumindai.service.LoginContext;
import edumindai.service.RegisterContext;
import edumindai.service.UserService;
import edumindai.service.UserTopicAssociationService;
import edumindai.utils.ContextHolder;
import edumindai.utils.JwtUtil;
import edumindai.utils.RegexCheckUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
* @author ljz20
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-04-30 16:14:17
*/
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    AuthenticationManager authenticationManager;

    @Autowired
    RegisterContext registerContext;

    @Autowired
    UserTopicAssociationService userTopicAssociationService;

    @Override
    public Response<LoginVO> login(LoginRequest loginRequest) {


        // 传入信息进行认证 魔改选择,加个选择器(context)然后枚举选择然后传入传出
        LoginContext loginContext = new LoginContext(loginRequest);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =loginContext.getUsernamePasswordAuthenticationToken();

        //security发起验证
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //security校验成功不会返回null,校验失败会返回null

        if(Objects.isNull(authenticate)){

            //密码错误
           throw new LoginServiceException(LoginExceptionEnum.PASSWORD_ERROR);
        }

        //authenticate通过验证已经将用户信息保存在线程中,SecurityContextHolder,自己用直接的信息,
        //注意更新信息
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        User user = ContextHolder.getUser();


        String token = JwtUtil.generateJwtToken(user);

        //登陆成功
       return new Response<>(1, "登陆成功",new LoginVO(token));
    }

    @Override
    public Response<RegisterVO> register(RegisterRequest registerRequest) {



        //选择策略,验证并获取用户对象
        User user = registerContext.registerOperation(registerRequest);

        //得到用户对象,保存在数据库
        saveUser(user);

        //创建token返回token


        String token = JwtUtil.generateJwtToken(user);

        return new Response<>(1,"注册成功",new RegisterVO(token));
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

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.QUERY_USER_ERROR);
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

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.QUERY_USER_ERROR);
        }

    }


    /**
     * 保存用户
     * @param user
     */
    public void saveUser(User user){

        try {

            userMapper.insertUser(user);

        }catch (Exception e){

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.SQL_INSERT_ERROR);
        }
    }

    /**
     * 创建用户,用于提供给策略模式接口实现类创建用户,因为大部分代码重复,所以邮箱注册实现类和手机注册实现类一致
     * @param registerRequest
     * @return
     */
    public static User createUser(RegisterRequest registerRequest){
        User user = new User();

        BeanUtils.copyProperties(registerRequest,user);

        //用户密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encode);

        user.setStatus(UserStatusEnum.NORMAL);

        user.setId(UUID.randomUUID().toString());

        user.setTypes(UserTypeEnum.ORDINARY);

        return user;
    }

//    /**
//     * 验证账号是否存在
//     * @param key 邮箱号或者手机号
//     * @return ture就是有
//     */
//    public static boolean verificationHave(String key){
//        //判断是否是邮箱类型
//        boolean emailType = RegexCheckUtil.isEmail(key);
//
//        if (emailType){
//
//            //邮箱查看是否存在
//        }else {
//
//            //手机号查看是否存在
//
//
//        }
//
//    }

    @Override
    public Response getTopics() {

        String userId = ContextHolder.getUser().getId();

        List<UserTopicAssociation> myUserTopicAssociation = userTopicAssociationService.findMyTopic(userId);

        TopicsVO topicsVO = new TopicsVO();
        topicsVO.setUserId(userId);

        ArrayList<String> topics = new ArrayList<>();

        topicsVO.setList(topics);

        for (UserTopicAssociation userTopicAssociation : myUserTopicAssociation){

            topics.add(userTopicAssociation.getTopicId());

        }


        return new Response(1,"查询成功",topicsVO);
    }
}




