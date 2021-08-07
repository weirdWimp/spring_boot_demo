package com.example.springboot.demo.config;

import com.example.springboot.demo.controller.http.AuthUserArgumentResolver;
import com.example.springboot.demo.interceptor.BasicAuthCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author guo
 * @date 2021/8/7
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * Aside from the pathPatterns used here, we can create a custom handler method annotation as a marker
     * to decide whether apply this handler interceptor
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BasicAuthCheckInterceptor())
                .addPathPatterns("/validation/*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserArgumentResolver());
    }
}
