package com.example.springboot.demo.cglib;

/**
 * @author guo
 * @date 2021/7/17
 */
public class A {

    public String doBusiness() {
        System.out.println("A do business");
        doInnerBusiness();
        return "A";
    }

    public String doInnerBusiness() {
        System.out.println("A do doInnerBusiness");
        return "InnerA";
    }

}
