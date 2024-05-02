package edumindai.service.impl.security;

import edumindai.enums.exception.RegisterExceptionEnum;
import edumindai.exception.RegisterServiceException;
import edumindai.mapper.UserMapper;
import edumindai.model.entity.User;
import edumindai.utils.RegexCheckUtil;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * security根据名称获取对象的实现类
 * 认证流程的步骤五: 5.调用loadUserByUsername方法查询用户
 * 返回UserDetails,这个是一个接口,是封装了用户的信息以及权限信息的
 */
@Component
public class LoadUserUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户
     *
     * @param username 这个是前面传来的,也就是security认证名称,后续根据邮箱还是手机号进行策略分开
     * @return UserDetails 这个接口封装了用户的信息,包括账号和密码以及权限,后续会根据这个判断输入的账号密码时候和这个相符
     * 不相符的话,security会抛出异常
     * @throws UsernameNotFoundException 用户找不到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        //判断是否是邮箱,是的话是邮箱登陆
        if (RegexCheckUtil.isEmail(username)) {

            User user = userMapper.findUserByEmail(username);


            return new EmailLoginUserDetailsImpl(user);


        } else if (RegexCheckUtil.isPhone(username)) {
            //判断是否是手机号,是的话是手机号登陆

            User user = userMapper.findUserByPhone(username);

            return new PhoneLoginUserDetailsImpl(user);
        }

        throw new RegisterServiceException(RegisterExceptionEnum.LOGIN_PARAMETER_ERROR);

    }
}
