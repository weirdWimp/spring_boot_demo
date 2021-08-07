package com.example.springboot.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guo
 * @date 2021/8/1
 */
@Slf4j
@Controller
@RequestMapping("/dispatcher/servlet")
public class DispatcherServletTestController {

    @RequestMapping(value = "request", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public ModelAndView request(HttpServletRequest httpServletRequest) {
        Object contextObj = httpServletRequest.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        log.info("request attribute {}:{}", DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, contextObj);
        return new ModelAndView("ok");
    }
}
