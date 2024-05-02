package edumindai.service;

import edumindai.enums.RegisterPattern;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.service.impl.EmailRegisterStrategy;
import edumindai.service.impl.PhoneRegisterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 注册策略接口选择, 根据注册方式选择不同的策略,这个是选择不同策略然后包装
 */
@Component
public class RegisterContext {

    private final Map<RegisterPattern, RegisterStrategy> registerStrategies;



    public RegisterContext(@Autowired EmailRegisterStrategy emailRegisterStrategy,@Autowired PhoneRegisterStrategy phoneRegisterStrategy) {
        Map<RegisterPattern, RegisterStrategy> registerPatternRegisterStrategyMap = new HashMap<>();

        registerPatternRegisterStrategyMap.put(RegisterPattern.Phone,phoneRegisterStrategy);
        registerPatternRegisterStrategyMap.put(RegisterPattern.Email, emailRegisterStrategy);

        this.registerStrategies = registerPatternRegisterStrategyMap;
    }
    /**
     * 注册操作
     *
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    public User registerOperation(RegisterRequest registerRequest) {

        RegisterStrategy registerStrategy = registerStrategies.get(registerRequest.getRegisterPattern());

        return registerStrategy.registerOperation(registerRequest);
    }
}
