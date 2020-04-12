package com.example.springboot.demo.service;

import org.springframework.stereotype.Component;


/**
 * Service Component without implementing any interface.
 * Just for testing aop advice. Programming to interface is
 * a good practice.
 */
@Component
public class ConcreteTypeService {

    public void getDescription() {
        System.out.println("This is " + getClass().getSimpleName());
    }

}
