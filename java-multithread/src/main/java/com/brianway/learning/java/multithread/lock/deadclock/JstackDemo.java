package com.brianway.learning.java.multithread.lock.deadclock;

import javax.swing.plaf.TableHeaderUI;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-04-15
 */
public class JstackDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLockclass(true));
        Thread t2 = new Thread(new DeadLockclass(false));
        t1.start();
        t2.start();
    }

}

class DeadLockclass implements Runnable {
    public boolean falg;
    DeadLockclass(boolean falg){
        this.falg = falg;
    }

    public void run(){
        /**
         * 如果falg 值为true 则调用t1线程
         */
        if(falg){
            while (true){
                synchronized (Suo.o1){
                    System.out.println("o1" + Thread.currentThread().getName());
                    synchronized (Suo.o2){
                        System.out.println("o2" + Thread.currentThread().getName());
                    }
                }
            }
        }else {
            while (true){
                synchronized (Suo.o2){
                    System.out.println("o2" + Thread.currentThread().getName());
                    synchronized (Suo.o1){
                        System.out.println("o1 " + Thread.currentThread().getName());
                    }
                }
            }
        }


    }
}

class Suo {
    static Object o1 = new Object();
    static Object o2 = new Object();
}
