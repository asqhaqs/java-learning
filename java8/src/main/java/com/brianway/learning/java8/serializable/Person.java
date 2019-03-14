package com.brianway.learning.java8.serializable;

import java.io.Serializable;

/**
 * Create by xudong
 * Date: 2019-03-14
 */
public class Person implements Serializable {
    private int age;
    private String sex;
    private String name;
    private String hobby;
    private String id;

    // 序列化ID
    private static final long serialVersionUID = 1L;

    public Person(){

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }





}
