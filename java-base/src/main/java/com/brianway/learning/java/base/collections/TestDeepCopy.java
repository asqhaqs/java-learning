package com.brianway.learning.java.base.collections;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2022-02-24
 */
public class TestDeepCopy {
    public static void main(String[] args) {
        Map<String, Object> map1 = Maps.newHashMap();
        map1.put("1", "sssss");
        Map<String, Object> map2 = Maps.newHashMap(map1);
        map1.put("1", "fuck");
        System.out.println(map2);
        System.out.println(map1);
    }
}
