package com.example.springboot.demo.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class StaticResourceEntity {

    /**
     * Resource as dependency, string path wil be converted to Resource object
     */
    @Value("classpath:application.properties")
    private Resource resource;

    /**
     * Conversion Service, SpEL also can be used here
     */
    @Value("${values.list}")
    private List<String> values;

    /**
     * SpEL @Value("#{ {key1:'value1',key2:'value2'} }")
     */
    @Value("#{ ${values.map} }")
    private Map<String, String> map;

    /**
     * also @Value("${user.dir}") from Spring's environment abstract
     */
    @Value("#{ systemProperties['user.dir'] }")
    private String userDir;

    @PostConstruct
    private void afterPropertiesSet() {
        System.err.println(getClass() + " resource exists:" + resource.exists());
        for (String value : values) {
            System.err.println("value from List:" + value);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("from map, " + entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("userDir = " + userDir);
    }

}
