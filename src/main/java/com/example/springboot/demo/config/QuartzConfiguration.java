package com.example.springboot.demo.config;

import com.example.springboot.demo.task.CustomQuartzJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.HashMap;

@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean detailFactoryBean = new JobDetailFactoryBean();
        detailFactoryBean.setJobClass(CustomQuartzJob.class);
        detailFactoryBean.setName("PrintJob");
        detailFactoryBean.setGroup("Group1");
        HashMap<String, Object> map = new HashMap<>();
        map.put("timeout", "10");
        detailFactoryBean.setJobDataAsMap(map);
        return detailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean simpleTrigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetail);
        simpleTriggerFactoryBean.setStartDelay(1000);
        simpleTriggerFactoryBean.setRepeatInterval(2000);
        return simpleTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(Trigger trigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(trigger);
        return schedulerFactoryBean;
    }
}
