package com.brianway.learning.java8.effective.optional.mytest;

import java.util.Optional;

/**
 * Create by xudong
 * Date: 2019-03-14
 */
public class OptionalTest {


    public static void main(String[] args) {
        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("full name is set? " + fullName );
        System.out.println("full name :" + fullName.orElseGet(()->"[name]"));
        System.out.println("full name is set? " + fullName.isPresent());
        System.out.println(fullName.map(s -> "Hei " + s + "!").orElse("Hey ZU"));
    }
}
