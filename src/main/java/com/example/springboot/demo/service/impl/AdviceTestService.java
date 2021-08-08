package com.example.springboot.demo.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author guo
 * @date 2021/7/18
 */
@Service
public class AdviceTestService {

    public String getThings() {
        throwEx();
        return "I1";
    }

    private void throwEx() {
        throw new IllegalArgumentException("s");
    }

}
