package com.example.springboot.demo.properties;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

// @Component
public class ConfigPropertiesLoader implements ResourceLoaderAware, EnvironmentAware {

    private static final String FORMAT = "classpath:*.properties";

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadPropsToEnv() throws IOException {
        ResourcePatternResolver patternResolver;
        if (resourceLoader instanceof ResourcePatternResolver) {
            patternResolver = ((ResourcePatternResolver) resourceLoader);
        } else {
            patternResolver = new PathMatchingResourcePatternResolver();
        }

        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();

        String[] activeProfiles = configurableEnvironment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            Resource[] resources = patternResolver.getResources(String.format(FORMAT, activeProfile));
            for (Resource resource : resources) {
                ResourcePropertySource propertySource = new ResourcePropertySource(resource);
                propertySources.addLast(propertySource);
            }
        }

        System.out.println("datasource.ds1.user = " + environment.getProperty("datasource.ds1.user"));
    }


}
