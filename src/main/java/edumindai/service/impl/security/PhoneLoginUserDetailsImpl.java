package edumindai.service.impl.security;

import edumindai.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * security用来封装用户信息的类, 需要实现UserDetails接口
 * 根据这个接口的方法去get 用户名和密码(真实的),然后去和输入的去对比
 * 认证步骤步骤7
 */
public class PhoneLoginUserDetailsImpl implements UserDetails {
    private User user;

    public PhoneLoginUserDetailsImpl(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    /**
     * 获取权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户名称,这里面是使用手机号登陆的实现, 所以这里返回手机号
     * @return
     */
    @Override
    public String getUsername() {
        return user.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
