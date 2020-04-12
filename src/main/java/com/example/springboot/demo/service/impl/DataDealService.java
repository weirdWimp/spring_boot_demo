package com.example.springboot.demo.service.impl;

import com.example.springboot.demo.service.DealService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("DataDealService")
public class DataDealService implements DealService {

    @Transactional
    @Override
    public void deal() {
    }

    @Override
    public String getDescription() {
        return "This is from " + this.getClass().getSimpleName();
    }

    public String getSelfDescription() {
        return "This is from " + this.getClass().getSimpleName() + " getSelfDescription";
    }

    public void print(String string) {
        System.out.println("Print: " + string);
    }

}
