package com.example.springboot.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guo
 * @date 2021/4/15
 */
@RestController
@RequestMapping("form")
public class UploadController {

    @PostMapping("register")
    public void upload(@RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType) {
        System.out.println("contentType = " + contentType);
        // httpHeaders.toSingleValueMap().forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
