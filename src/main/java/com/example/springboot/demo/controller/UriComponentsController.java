package com.example.springboot.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

/**
 * @author guo
 * @date 2021/8/5
 */
@RestController
@RequestMapping("/uri")
public class UriComponentsController {

    @RequestMapping("hotel/{hotel}")
    public String hotel(@PathVariable("hotel") String hotel, @RequestParam("q") String q) {
        URI uri = MvcUriComponentsBuilder.fromMethodName(UriComponentsController.class, "home", "chengdu", "CS")
                .build()
                .encode()
                .toUri();

        System.out.println(uri.toString());
        return String.format("[%s] - [%s]", hotel, q);
    }

    @RequestMapping("home/{hotel}")
    public String home(@PathVariable("hotel") String hotel, @RequestParam("q") String q) {
        return String.format("[%s] - [%s]", hotel, q);
    }
}
