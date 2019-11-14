package com.brianway.learning.java.jvm.thread;

/**
 * 线程死锁状态演示
 * Create by xudong
 * Author: xudong
 * Date: 2019-11-14
 */
public class DeadLock {
    static class SynAddRunable implements Runnable{
        int a, b;
        public SynAddRunable(int a, int b){
            this.a = a;
            this.b = b;
        }


        @Override
        public void run() {
            synchronized (Integer.valueOf(a)){
                synchronized (Integer.valueOf(b)){
                    System.out.println(a + b);
                }
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            new Thread(new SynAddRunable(1,2)).start();
            new Thread(new SynAddRunable(2,1)).start();
        }
    }


}




