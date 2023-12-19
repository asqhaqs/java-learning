package com.brianway.learning.java.base.lomback;

import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-11-01
 */
@Data
public class TestCase {
    private String key;
    private List<Holder> holderList;


    @Data
    public static class Holder {
        private int value;
    }

    public static void main(String[] args) {
        Holder holder = new Holder();
        holder.setValue(1);
        Holder holder2 = new Holder();
        holder2.setValue(2);
        Holder holder3 = new Holder();
        holder3.setValue(3);
        TestCase testCase = new TestCase();
        testCase.setKey("testKey");
        List<Holder> list = Lists.newArrayList();
        list.add(holder);
        list.add(holder3);
        list.add(holder2);
        testCase.setHolderList(list);
        List<Holder> newList = list.stream().sorted(Comparator.comparing(Holder::getValue)).collect(Collectors.toList());
        System.out.println(list);
        System.out.println(newList);
        System.out.println(testCase);
    }
}
