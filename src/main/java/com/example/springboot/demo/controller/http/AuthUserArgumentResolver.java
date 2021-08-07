package com.example.springboot.demo.controller.http;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.example.springboot.demo.interceptor.BasicAuthCheckInterceptor.AUTH_USER_ATTRIBUTE_NAME;

/**
 * @author guo
 * @date 2021/8/7
 */
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return Objects.requireNonNull(nativeRequest).getAttribute(AUTH_USER_ATTRIBUTE_NAME);
    }
}
