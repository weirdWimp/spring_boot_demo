package com.example.springboot.demo.interceptor;

import com.example.springboot.demo.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * fine-grained handler-related pre-processing tasks are candidates for HandlerInterceptor implementations,
 * especially factored-out common handler code and authorization checks
 * <p>
 * 与细粒度处理程序相关的预处理任务更适合使用 HandlerInterceptor, 尤其是抽取出来的处理器方法的公共代码以及授权检查
 *
 * @author guo
 * @date 2021/8/7
 * @see org.springframework.web.servlet.HandlerInterceptor
 */
@Slf4j
public class BasicAuthCheckInterceptor extends HandlerInterceptorAdapter {

    public static final String AUTH_USER_ATTRIBUTE_NAME = "AuthUser";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // can use handler method decide whether to apply this interceptor behaviour
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (StringUtils.isBlank(authorization) || !authorization.matches("Basic .*")) {
            throw new UnAuthorizedException("UnAuthorized Request", requestURI);
        } else {
            try {
                String username = basicAuthInfoResolve(authorization);
                log.info("User:{} request {}", username, requestURI);
                request.setAttribute(AUTH_USER_ATTRIBUTE_NAME, username);
            } catch (Exception e) {
                log.error("Invalid Authorization Format", e);
                throw new UnAuthorizedException("Invalid Authorization Format", requestURI);
            }
        }
        return true;
    }

    private String basicAuthInfoResolve(String authorization) {
        byte[] decodeBytes = Base64.getDecoder().decode(authorization.split("\\s")[1].getBytes(StandardCharsets.UTF_8));
        String authInfo = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] split = authInfo.split(":");
        String username = split[0];
        String password = split[1];
        return username;
    }
}
