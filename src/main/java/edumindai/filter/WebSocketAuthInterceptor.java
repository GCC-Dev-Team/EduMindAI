package edumindai.filter;

import edumindai.enums.RegisterPattern;
import edumindai.model.entity.User;
import edumindai.service.impl.security.EmailLoginUserDetailsImpl;
import edumindai.service.impl.security.PhoneLoginUserDetailsImpl;
import edumindai.utils.ContextHolder;
import edumindai.utils.IflytekWebsocketServerUtil;
import edumindai.utils.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;
@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {


        String token = IflytekWebsocketServerUtil.getToken(request.getURI().getQuery());

        if (token != null) {

            //token获取用户
            User userFromToken = JwtUtil.getUserFromToken(token);

            //获取注册模式
            assert userFromToken != null;

            RegisterPattern registerPattern = userFromToken.getRegisterPattern();

            //区分这个是邮箱注册还是手机号注册

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

            if (registerPattern == RegisterPattern.Email) {

                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(new EmailLoginUserDetailsImpl(userFromToken), null, null);

            } else if (registerPattern == RegisterPattern.Phone) {

                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(new PhoneLoginUserDetailsImpl(userFromToken), null, null);

            }

            // 根据 Token 执行认证逻辑
            //security发起验证
            Authentication authenticate = usernamePasswordAuthenticationToken;

            // 将认证信息添加到 SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            //通过鉴权
            return true;

        }

        //鉴权失败,token为null

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        System.out.println("我我收了");
    }
}
