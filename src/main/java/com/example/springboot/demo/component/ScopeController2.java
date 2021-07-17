package com.example.springboot.demo.component;

/**
 * @author guo
 * @date 2021/7/10
 */

import com.example.springboot.demo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author guo
 * @date 2021/7/10
 */
@RestController
@RequestMapping("scope2")
@Slf4j
public class ScopeController2 {

    @Autowired
    private UserInfo userInfo;

    /**
     * 实际注入的是 Spring CGLIB 为 RequestScopeEntity 创建的一个代理对象，
     * 可以通过打印该对象的 Class 对象的名称来验证，针对每个请求，都会创建一个
     * 实际的 RequestScopeEntity 对象，然后将对这个代理对象方法的调用委托给
     * 新创建的 RequestScopeEntity 对象
     */
    @Autowired
    private RequestScopeEntity requestScopeEntity;

    @GetMapping("user")
    public String userInfo() {
        requestScopeEntity.doRequestBusiness();

        int userInfoHash = userInfo.hashCode();
        int scopeEntityHash = requestScopeEntity.hashCode();

        log.info("[scope2], userInfo:[{}], requestScopeEntity:[{}], class:[{}]", userInfoHash, scopeEntityHash, requestScopeEntity.getClass().getSimpleName());
        return userInfoHash + " " + scopeEntityHash;
    }

    @PostConstruct
    public void init() {
        log.info("scope2 userInfo:" + requestScopeEntity.hashCode());
    }
}
