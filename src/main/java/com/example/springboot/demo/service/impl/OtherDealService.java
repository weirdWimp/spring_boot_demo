package com.example.springboot.demo.service.impl;

import com.example.springboot.demo.service.DealService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("OtherDealService")
public class OtherDealService implements DealService {

    @Override
    public void deal() {
    }

    @Override
    public String getDescription() {
        return "This is " + getClass().getSimpleName();
    }

    public String getSpecDescription() {
        return "This is " + getClass().getSimpleName() + " " + LocalDateTime.now();
    }
}
