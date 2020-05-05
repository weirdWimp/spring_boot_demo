package com.example.springboot.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 仅作 Jackson 反序列化时使用，当从第三方接口调用返回数据时，为了不改变原来的结构，又能方便反序列化成对象，
 * 填充属性的值，序列化会时出现具有相同意义字段的值。
 */
public class RequestPerson extends Person {

    @JsonProperty("NAME")
    private String requestName;

    @JsonProperty("AGE")
    private int requestAge;

    @JsonProperty("GENDER")
    private String requestGender;

    @JsonProperty("ADDRESS")
    private String requestAddress;

    public RequestPerson() {
    }

    /**
     * ObjectMapper依赖于Java对象的默认的无参构造函数进行反序列化，并且严格地通过getter和setter的命名规约进行序列化和反序列化。
     * 因此这里通过反序列化时调用 setter 方法设置父类的属性，达到填充的目的
     */
    public void setRequestName(String requestName) {
        setName(requestName);
    }

    public void setRequestAge(int requestAge) {
        setAge(requestAge);
    }

    public void setRequestGender(String requestGender) {
        setGender(requestGender);
    }

    public void setRequestAddress(String requestAddress) {
        setAddress(requestAddress);
    }

    public String getRequestName() {
        return getName();
    }

    public int getRequestAge() {
        return getAge();
    }

    public String getRequestGender() {
        return getGender();
    }

    public String getRequestAddress() {
        return getAddress();
    }
}
