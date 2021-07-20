package com.example.springboot.demo.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author guo
 * @date 2021/7/20
 */
@Slf4j
public class LogAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.info("[[Spring AOP API After Returning Debug Advice]] - " + method.getName());
    }
}
