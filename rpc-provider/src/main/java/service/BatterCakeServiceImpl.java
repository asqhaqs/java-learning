package service;

import service.impl.BatterCakeService;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-25
 */
public class BatterCakeServiceImpl implements BatterCakeService {

    @Override
    public String sellBatterCake(String name){
        return name + "煎饼， 特别好吃";
    }
}
