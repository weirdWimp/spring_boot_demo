package com.example.springboot.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleTask {

    @Scheduled(fixedRate = 2000)
    public void execute() {
        // System.out.println("Now:" + LocalDateTime.now());
    }

}
