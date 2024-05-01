package edumindai.model.dto;

import edumindai.enums.RegisterStrategyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /**
     * 手机号 可选
     */
    private String phoneNumber;

    /**
     * 邮箱 可选
     */
    private String email;
    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verificationCode;
    /**
     * 注册模式
     */
    private RegisterStrategyType registerPattern;
}
