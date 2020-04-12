package com.example.springboot.demo.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RedisUtil implements EnvironmentAware, InitializingBean {

    private static final String REDIS_PREFIX = "redis.";
    private static final String REDIS_NAME_FORMAT = "redis.%s";

    private Environment environment;

    private Map<String, RedisTemplate> redisTemplateMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        Set<String> redisNames = propertySources.stream().filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(propertySource -> ((EnumerablePropertySource<?>) propertySource).getPropertyNames())
                .flatMap(Stream::of).filter(key -> key.startsWith(REDIS_PREFIX))
                .map(key -> key.split("\\.")[1]).collect(Collectors.toSet());

        Binder binder = Binder.get(environment);
        for (String redisName : redisNames) {
            String prefix = String.format(REDIS_NAME_FORMAT, redisName);
            RedisProperties redisProperties = binder.bind(prefix, Bindable.of(RedisProperties.class)).get();
            System.out.println("redisProperties cluster nodes: " + redisProperties.getCluster().getNodes());
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
