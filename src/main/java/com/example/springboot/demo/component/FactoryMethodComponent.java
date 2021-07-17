package com.example.springboot.demo.component;

import com.example.springboot.demo.entity.UserInfo;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * `@Component` 类中的 `@Bean` 方法的处理与 `@Configuration` 中的处理不同, 区别在于对于 `@Component` 类，
 * CGLIB 并不会拦截对方法和字段的调用，而在 `@Configuration` 类中，CGLIB 的代理是通过对 `@Bean` 方法中的方法和字段
 * 调用来创建对协作对象（依赖）的 bean 元数据引用（bean metadata references）, 这样的方法并不是通过一般的 Java 语义
 * 进行调用的，而是通过容器来提供声明周期管理和 Spring beans 的代理， 即使是通过程序式的调用某个 `@bean` 方法来引用该 bean.
 * <p>
 * 在 `Component` 类中的某个 `@Bean` 方法中，调用其它方法或字段，只是标准的 Java 方法调用语义，并没有特殊的 CGLIB 的处理和约束.
 *
 * @author guo
 * @date 2021/7/10
 */
@Component
public class FactoryMethodComponent {

    /**
     * 声明一个 InjectionPoint（或者是子类 DependencyDescriptor）类型的对象作为工厂方法的参数，可以用来访问
     * 触发当前 bean 创建的注入点, 但是这只适用于 bean 实例实际的创建，而不是对已存在实例的注入。所以在 prototype bean
     * 的场景下意义比较大
     * <p>
     * <p>
     * scopeName = prototype 指定为原型 bean
     *
     * @param injectionPoint
     * @return
     */
    @Bean
    @Scope(scopeName = "prototype")
    public UserInfo userInfo(InjectionPoint injectionPoint) {
        System.out.println("prototype instance for " + injectionPoint.getMember());
        return new UserInfo();
    }

    @Bean
    public PropertiesFactoryBean overrideProperties(@Autowired ResourceLoader resourceLoader,
                                                    @Autowired UserInfo userInfo) {

        System.out.println("inject userInfo from overrideProperties:" + userInfo.hashCode());
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(resourceLoader.getResource("classpath:override.properties"));
        propertiesFactoryBean.setFileEncoding("UTF-8");
        return propertiesFactoryBean;
    }

    @Bean
    public PropertiesFactoryBean applicationProperties(@Autowired ResourceLoader resourceLoader,
                                                       @Autowired UserInfo userInfo) {

        System.out.println("inject userInfo from applicationProperties:" + userInfo.hashCode());
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(resourceLoader.getResource("classpath:application.properties"));
        propertiesFactoryBean.setFileEncoding("UTF-8");
        return propertiesFactoryBean;
    }

}
