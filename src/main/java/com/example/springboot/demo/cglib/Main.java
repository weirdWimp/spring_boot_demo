package com.example.springboot.demo.cglib;

/**
 * @author guo
 * @date 2021/7/17
 */
public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        A a = CGlibContainer.createInstance(A.class);
        a.doBusiness();
    }

}
