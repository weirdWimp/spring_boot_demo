package com.example.springboot.demo.component;

import com.example.springboot.demo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("scope1")
@Slf4j
public class ScopeController1 {

    @Autowired
    private UserInfo userInfo;

    @GetMapping("user")
    public String userInfo() {
        String name = userInfo.getName();
        String hash = userInfo.hashCode() + "";
        log.info("[scope1], userInfo:[{}]", hash);
        return hash;
    }

    @PostConstruct
    public void init() {
        log.info("scope1 userInfo:" + userInfo.hashCode());
    }
}
