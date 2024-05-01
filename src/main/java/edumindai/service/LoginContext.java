package edumindai.service;


import edumindai.enums.exception.LoginExceptionEnum;

import edumindai.exception.LoginServiceException;

import edumindai.model.dto.LoginRequest;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//登陆接口的策略选择
@Data
public class LoginContext {

    /**
     * security的登陆器,输入账号名称和密码
     * 步骤一
     */
    private final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    public LoginContext(LoginRequest loginRequest) {
        switch (loginRequest.getRegisterPattern()) {
            case Phone:

                this.usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword());
                break;

            case Email:
                this.usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
                break;

            default:
                throw new LoginServiceException(LoginExceptionEnum.LOGIN_STRATEGY_TYPE_ERROR);
        }

    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {

        return usernamePasswordAuthenticationToken;
    }
}
