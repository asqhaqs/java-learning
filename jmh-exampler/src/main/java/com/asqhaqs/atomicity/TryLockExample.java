package com.asqhaqs.atomicity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

import static java.lang.Thread.currentThread;

public class TryLockExample {
    private final static Object VAL_OBJ = new Object();

    public static void main(String[] args) {
        final TryLock lock = new TryLock();
        final List<Object> validation = new ArrayList<>();
        // 启动10个线程， 并且不断进行锁的获取以及释放操作
        for(int i = 0; i < 10; i++) {
             new Thread( () -> {
                 while (true) {
                     try {
                         // 尝试获取锁, 该方法不会导致当前线程进入阻塞
                         if (lock.tryLock()){
                             System.out.println(currentThread() + ": get the lock.");
                             if (validation.size() > 1){
                                 throw new IllegalAccessException("validation failed. ");
                             }
                             validation.add(VAL_OBJ);
                             TimeUnit.MILLISECONDS.sleep(current().nextInt(10));
                         }else {
                             // 如果没有获取锁, 做个休眠，防止死机
                             TimeUnit.MILLISECONDS.sleep(current().nextInt(10));
                         }
                     } catch (InterruptedException | IllegalAccessException e) {
                         e.printStackTrace();
                     } finally {
                         // 释放锁
                         if (lock.release()){
                             System.out.println(currentThread() + ": release the lock.");
                             validation.remove(VAL_OBJ);
                         }
                     }
                 }
             }).start();
        }
    }
}
