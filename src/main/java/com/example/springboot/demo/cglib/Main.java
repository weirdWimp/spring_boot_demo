package com.example.springboot.demo.cglib;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author guo
 * @date 2021/7/17
 */
public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\DevelopmentKit\\idea-workspace\\java-workspace\\spring-boot-demo\\cglib");
        // A a = CGlibContainer.createInstance(A.class);
        // a.doBusiness();
        springAopApiTest();
    }

    /**
     * @see org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#intercept(Object, Method, Object[], MethodProxy)
     * @see org.springframework.aop.framework.CglibAopProxy.CglibMethodInvocation#proceed()
     * @see org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor
     * @see org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor
     * @see org.aopalliance.intercept.MethodInterceptor
     */
    public static void springAopApiTest() {
        A a = new A();
        ProxyFactory proxyFactory = new ProxyFactory(a);
        proxyFactory.addAdvice(new LogBeforeAdvice());
        proxyFactory.addAdvice(new LogAfterReturningAdvice());
        proxyFactory.addAdvice(new LogAfterReturningAdvice2());
        proxyFactory.addAdvice(new LogAroundAdvice());
        A proxyA = (A) proxyFactory.getProxy();
        proxyA.doBusiness();
    }
}
