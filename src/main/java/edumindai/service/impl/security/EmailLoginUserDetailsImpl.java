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
public class EmailLoginUserDetailsImpl implements UserDetails {

    private User user;

    public EmailLoginUserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public User getUser() {
        return user;
    }

    /**
     * 获取真实的用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取真实的用户名,这里使用邮箱作为用户名
     * @return
     */

    @Override
    public String getUsername() {
        return user.getEmail();
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
