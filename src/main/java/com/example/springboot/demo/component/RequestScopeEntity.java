package com.example.springboot.demo.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author guo
 * @date 2021/7/10
 */
@Slf4j
@Component
@RequestScope
public class RequestScopeEntity {

    public RequestScopeEntity() {
        log.info("############ Create RequestScopeEntity ############");
    }

    public void doRequestBusiness() {
        log.info(Thread.currentThread() + " do current request business");
    }
}
