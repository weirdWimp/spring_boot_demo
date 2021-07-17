package com.example.springboot.demo.cglib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guo
 * @date 2021/7/17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CGlibAspect {
    
    /**
     * 声明要为那些类进行增强，即切点
     *
     * @return
     */
    Class<?>[] value();

}
