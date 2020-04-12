package com.example.springboot.demo.entity;

import org.springframework.stereotype.Component;

@Component
public class PropertyOverrideClass {

    private String v1;

    private String v2;

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OverrideClass{");
        sb.append("v1='").append(v1).append('\'');
        sb.append(", v2='").append(v2).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
