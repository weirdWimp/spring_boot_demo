package com.example.springboot.demo.controller;

import com.example.springboot.demo.service.impl.TX1Service;
import com.example.springboot.demo.service.impl.TX2Service;
import com.example.springboot.demo.service.impl.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author guo
 * @date 2021/7/25
 */
@RestController
@RequestMapping("/dao/tx")
public class TransactionController {

    @Resource
    private TransactionTestService transactionTestService;

    @Resource
    private TX1Service tx1Service;

    @RequestMapping("propagation/required")
    public void propagationRequired() {
        // transactionTestService.outerTXBusiness();
        transactionTestService.nestedTXTest();
        tx1Service.outerTXBusiness();
    }
}
