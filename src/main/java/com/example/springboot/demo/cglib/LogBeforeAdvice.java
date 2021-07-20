package com.example.springboot.demo.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author guo
 * @date 2021/7/20
 */
@Slf4j
public class LogBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("[[Spring AOP API Before Debug Advice]] - " + method.getName());
    }
}
