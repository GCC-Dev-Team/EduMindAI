package edumindai.service;

import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;

/**
 * 注册策略接口
 */
public interface RegisterStrategy {
    /**
     * 注册操作
     * @param registerRequest
     * @return
     */
    User registerOperation(RegisterRequest registerRequest);
}
