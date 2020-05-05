package com.example.springboot.demo.entity;

public class UserInfo {

    private int id;

    private String uid;

    private String name;

    public UserInfo() {
    }

    public UserInfo(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
