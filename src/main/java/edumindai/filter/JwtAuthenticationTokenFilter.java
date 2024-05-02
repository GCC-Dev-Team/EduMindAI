package edumindai.filter;


import edumindai.enums.RegisterPattern;
import edumindai.model.entity.User;
import edumindai.service.impl.security.EmailLoginUserDetailsImpl;
import edumindai.service.impl.security.PhoneLoginUserDetailsImpl;
import edumindai.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    // 拦截请求

    /**
     * 拦截请求,验证token
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤链
     * @throws ServletException servlet异常
     * @throws IOException 输入输出异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 从请求头中获取 Token
        String token = extractTokenFromHeader(request);

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

        }


        // 继续处理请求
        filterChain.doFilter(request, response);

    }

    /**
     * 拿取token信息
     *
     * @param request
     * @return
     */
    private String extractTokenFromHeader(HttpServletRequest request) {

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7); // 去除 "Bearer " 前缀
        }
        return null;
    }
}
