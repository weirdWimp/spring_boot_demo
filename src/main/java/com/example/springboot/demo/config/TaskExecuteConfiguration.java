package com.example.springboot.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * `@EnableAsync` enables support for using annotation for task async execution.
 * `@EnableAsync` enables support for using annotation for task scheduling.
 *
 * For more fine-control over task scheduling, you can implement interface SchedulingConfigurer.
 *
 * @see org.springframework.scheduling.annotation.Async
 * @see org.springframework.scheduling.annotation.Scheduled
 * @see org.springframework.scheduling.annotation.SchedulingConfigurer
 */
@Configuration
@EnableAsync
@EnableScheduling
public class TaskExecuteConfiguration {

}
