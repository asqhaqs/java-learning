package com.brianway.learning.java.async.executors;

import com.brianway.learning.java.base.async.executors.ExecutorContext;
import com.brianway.learning.java.base.async.executors.ExecutorHelper;

/**
 * 可用于异步接口加速
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-10-10
 */
public class ExecutorContextTest {
    public static String runTest1() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("runTest1");
        return "runTest1";
    }

    public static String runTest2() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("runTest2");
        return "runTest2";
    }

    public static void main(String[] args) {
        ExecutorContext ctx = ExecutorHelper.submit("resultTest1", ExecutorContextTest::runTest1);
        ExecutorHelper.submit("resultTest2", ExecutorContextTest::runTest2, ctx);
        String result1 = ctx.getResult("resultTest1").toString();
        String result2 = ctx.getResult("resultTest2").toString();
        System.out.println(result1 + " " + result2);
    }
}
