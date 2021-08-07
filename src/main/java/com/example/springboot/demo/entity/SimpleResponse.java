package com.example.springboot.demo.entity;

import lombok.Data;

/**
 * @author guo
 * @date 2021/8/7
 */
@Data
public class SimpleResponse<T> {

    private T body;

    private String rtnCod;

    private String errMsg;

    public SimpleResponse(T body, String rtnCod, String errMsg) {
        this.body = body;
        this.rtnCod = rtnCod;
        this.errMsg = errMsg;
    }

    public static <T> SimpleResponse<T> success(T body) {
        return new SimpleResponse<>(body, "SUC0000", "");
    }

    public static <T> SimpleResponse<T> fail(String errMsg) {
        return new SimpleResponse<>(null, "ERR0000", errMsg);
    }
}
