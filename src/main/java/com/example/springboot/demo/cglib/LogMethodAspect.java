package com.example.springboot.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author guo
 * @date 2021/7/17
 */
@CGlibAspect({A.class, B.class})
public class LogMethodAspect {

    private static final Logger log = LoggerFactory.getLogger(LogMethodAspect.class);

    public static void before(Object object, Method method, Object[] args) {
        log.info("####### before {} executing, args: {}", method.getName(), Arrays.toString(args));
    }

    public static void after(Object object, Method method, Object[] args, Object result) {
        log.info("####### after {} executing, args: {}, result:{}", method.getName(), Arrays.toString(args), result);
    }
}
