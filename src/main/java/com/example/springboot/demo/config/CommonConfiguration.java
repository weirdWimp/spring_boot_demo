package com.example.springboot.demo.config;

import com.example.springboot.demo.properties.DisplayProperties;
import com.example.springboot.demo.servlet.SimpleDebugFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.URI;
import java.util.Properties;


/**
 * @PropertySource 注解的 value 属性不支持通配符，但是支持 `${}` 形式的占位符
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 */
@Configuration
@ComponentScan(basePackages = "com.example.springboot.demo.entity", includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.example.springboot.demo.entity.ComponentScanClass"))
@PropertySource(value = {"classpath:externalFields.properties"})
@EnableConfigurationProperties(DisplayProperties.class)
@EnableAspectJAutoProxy
@Slf4j
public class CommonConfiguration {

    /**
     * BeanFactoryPostProcessor implementation.
     * Spring boot will auto config a PropertySourcesPlaceholderConfigurer bean by default (Using ConditionalOnMissingBean).
     * Now we build a PropertySourcesPlaceholderConfigurer bean and set IgnoreUnresolvablePlaceholders to true to allow
     * resolve failure.
     * <p>
     * Inject ResourceLoader bean to get a resource object and add it to PropertySourcesPlaceholderConfigurer bean through
     * `setLocation` method.
     * <p>
     * By default, if it cannot find a property in the specified properties files,
     * it checks against Spring Environment properties and regular Java System properties.
     *
     * @see org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }

    /**
     * BeanFactoryPostProcessor implementation
     */
    @Bean
    public PropertyOverrideConfigurer overrideConfigurer(ResourceLoader resourceLoader) {
        PropertyOverrideConfigurer configurer = new PropertyOverrideConfigurer();
        configurer.setLocation(resourceLoader.getResource("classpath:override.properties"));
        Properties properties = new Properties();
        properties.put("propertyOverrideClass.v1", "value from local properties");
        configurer.setProperties(properties);
        configurer.setLocalOverride(true);
        return configurer;
    }


    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> contextListener() {
        ServletContextListener listener = new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent sce) {
                log.info("############[custom ServletContextListener]############: ServletConText is initializing " + sce);
            }
        };
        return new ServletListenerRegistrationBean<>(listener);
    }

    @Bean
    public FilterRegistrationBean<SimpleDebugFilter> simpleDebugFilter() {
        FilterRegistrationBean<SimpleDebugFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SimpleDebugFilter());
        registrationBean.setAsyncSupported(false);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registrationBean;
    }

    public RestTemplate restTemplate() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://example.com/hotels?q={q}")
                .encode()
                .build();

        URI uri = uriComponents.expand("123")
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
