package com.brianway.learning.java.jvm.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-11-14
 */
public class ThreadWatcher {

    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    ;
                }
            }
        }, "testBusyThread");
        thread.start();
    }


    /**
     * 线程锁等待演示
     */
    public static void createLockThread(final Object lock){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try{
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");

        thread.start();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object o = new Object();
        createLockThread(o);
    }


}
