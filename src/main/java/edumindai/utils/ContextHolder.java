package edumindai.utils;


import edumindai.model.entity.EmailLoginUserDetailsImpl;
import edumindai.model.entity.PhoneLoginUserDetailsImpl;
import edumindai.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class ContextHolder {

    /**
     * 获取用户 接口
     **/
    public static UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    //TODO 需要实现多态进行优化
    public static User getUser() {

        UserDetails userDetails = getUserDetails();


        if (RegexCheckUtil.isPhone(userDetails.getUsername())) {

            PhoneLoginUserDetailsImpl phoneLoginUserDetailsImpl = (PhoneLoginUserDetailsImpl) userDetails;

            return phoneLoginUserDetailsImpl.getUser();
        }else {

            EmailLoginUserDetailsImpl emailLoginUserDetailsImpl = (EmailLoginUserDetailsImpl) userDetails;

            return emailLoginUserDetailsImpl.getUser();

        }

    }
}
