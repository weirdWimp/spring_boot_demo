package com.example.springboot.demo;

import com.example.springboot.demo.config.TaskExecuteConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

public class ResourcesTest {


    public static void main(String[] args) {
        // classpathResource();
        // fileSystemResource();
        // resourceLoader();
        // resourcePattern();
        classpathAct();
    }

    private static void classpathResource() {
        ClassPathResource classPathResource = new ClassPathResource("META-INF/services/java.nio.file.spi.FileSystemProvider");
        System.out.println(classPathResource.exists()); // true
        try {
            // it residents in a jar archive so it will fail
            System.out.println(classPathResource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void classpathAct() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:beans*.xml");
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }

    private static void fileSystemResource() {
        String filePath = "F:/github-backup/";
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        Resource relative = fileSystemResource.createRelative("index.html");
        System.out.println(relative.exists());

        // warning: FileSystemXmlApplicationContext always treats location
        // as a relative path to working directory even though it starts with a slash.
        // 'file：///some' can be used to specific absolute path (URL format) when this application context as a ResourceLoader
        FileSystemXmlApplicationContext xmlApplicationContext = new FileSystemXmlApplicationContext();
        Resource resource = xmlApplicationContext.getResource("/pom.xml");
        System.out.println("resource = " + resource.exists());

    }

    private static void resourceLoader() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TaskExecuteConfiguration.class);
        Resource resource = applicationContext.getResource("classpath:application.properties");
        System.out.println(resource.exists());
    }

    private static void resourcePattern() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:/META-INF/**/*.properties");
            for (Resource resource : resources) {
                System.out.println("resource.getDescription() = " + resource.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
