package com.example.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * EnvironmentPostProcessor Implementation for customizing Environment: add active profile based PropertySource
 * to Environment
 */
public class ProfileBasedPropertiesLoader implements EnvironmentPostProcessor {

    private static final String PROPERTIES_FORMAT = "classpath*:/profile/%s/*.properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // select active profile names as the dir name
        String[] activeProfiles = environment.getActiveProfiles();

        MutablePropertySources propertySources = environment.getPropertySources();

        // A  ResourcePatternResolver implementation that is able to resolve a
        // specified resource location path into one or more matching Resources
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        for (String activeProfile : activeProfiles) {
            String resourceLocation = String.format(PROPERTIES_FORMAT, activeProfile);
            try {
                Resource[] resources = resourcePatternResolver.getResources(resourceLocation);
                for (Resource resource : resources) {
                    ResourcePropertySource propertySource = new ResourcePropertySource(new EncodedResource(resource, "UTF-8"));
                    propertySources.addLast(propertySource);
                    System.out.printf("resource: %s has been added to Environment.\n", resource.getDescription());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
