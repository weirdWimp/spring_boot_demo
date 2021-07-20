package com.example.springboot.demo.cglib;

import org.springframework.aop.framework.ProxyFactory;

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
