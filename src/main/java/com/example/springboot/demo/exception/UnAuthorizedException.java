package com.example.springboot.demo.exception;

/**
 * @author guo
 * @date 2021/8/7
 */
public class UnAuthorizedException extends RuntimeException {

    private String path;

    public UnAuthorizedException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
