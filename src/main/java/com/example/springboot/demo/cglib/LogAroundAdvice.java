package com.example.springboot.demo.cglib;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author guo
 * @date 2021/7/20
 */
@Slf4j
public class LogAroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("[[Around Advice Debug - Before]] - " + invocation.getMethod().getName());
        Object proceed = invocation.proceed();
        log.info("[[Around Advice Debug - After]] - " + invocation.getMethod().getName());
        return proceed;
    }
}
