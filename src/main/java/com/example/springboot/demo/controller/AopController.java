package com.example.springboot.demo.controller;

import com.example.springboot.demo.service.ConcreteTypeService;
import com.example.springboot.demo.service.impl.AdviceTestService;
import com.example.springboot.demo.service.impl.OtherDealService;
import com.example.springboot.demo.service.impl.DataDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/aop/advice")
public class AopController {

    @Autowired
    public DataDealService dataDealService;

    @Autowired
    @Qualifier("OtherDealService")
    public OtherDealService otherDealService;

    @Autowired
    public ConcreteTypeService testService;

    @Autowired
    private AdviceTestService adviceTestService;

    @GetMapping("/advice")
    @ResponseStatus(HttpStatus.OK)
    public void redirect() {
        System.out.println(dataDealService.getDescription());
        // System.out.println(dataDealService.getSelfDescription());
        dataDealService.deal();
        dataDealService.print("create your magic");
    }


    @GetMapping("/ex")
    @ResponseStatus(HttpStatus.OK)
    public void exception() {
        try {
            String things = adviceTestService.getThings();
        } catch (Exception e) {
            System.out.println("Catch exception" + e.getMessage());
        }
    }



}
