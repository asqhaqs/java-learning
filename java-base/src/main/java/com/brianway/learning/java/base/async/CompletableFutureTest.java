package com.brianway.learning.java.base.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-10-09
 */
public class CompletableFutureTest {

    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureTest.class);
    public void future() {
        try {
            FutureTask<String> futureTask = new FutureTask<>(() -> "future begin");
            new Thread(futureTask).start();
            System.out.println(futureTask.get());
        } catch (Exception e) {
            System.out.println("future() error: " + e.getMessage());
        }
    }

    public void completableFutureRunAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("无返回值 completableFutureRunAsync() 开始异步执行"));
        future.join(); // 和get区别是 不会抛出异常
    }

    public void completableFutureSupplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("有返回值 completableFutureSupplyAsync() 开始异步执行");
                    return "异步执行";
                });
        String join = future.join(); // 和get区别是 不会抛出异常
        System.out.println(join);
    }

    public void thenApply() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> "string").thenApply(v -> ("打印" + v));
        System.out.println(future.join());
    }

    public void thenApplyException() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String[] result = new String[] {"1", "2", "3"};
            return result[3];
        }).thenApply(v -> ("执行 thenApplyException " + v));
        System.out.println(completableFuture.join());
    }

    public void exceptionApply() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String[] result = new String[] {"1", "2", "3"};
            return result[3];
        }).thenApply(v -> ("执行 thenApplyException " + v)).exceptionally(v -> {
            System.out.println("出现了异常，需要返回默认值， 并打印：" + v);
            return "返回默认值 1";
        });
        System.out.println(completableFuture.join());
    }

    public void handleException() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String[] result = new String[] {"1", "2", "3"};
            return result[2];
        }).handle((v, e) -> {
            if (e instanceof Exception) {
                System.out.println("抛出异常" + e);
                return "-1";
            } else {
                return "执行handle方法 上一个结果：" + v;
            }
        });
        System.out.println(completableFuture.join());
    }

    public void thenCombine() {
        try {
            CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                System.out.println("执行 future1() ");
                return "hello";
            });
            CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                System.out.println("执行 future2() ");
                return "world";
            });
            CompletableFuture<String> result = future1.thenCombine(future2, (a, b) -> {
                System.out.println("执行 future1 thenCombine future2");
                return a + " " + b;
            });
            System.out.println(result.get());
        } catch (Exception e) {
            logger.error("thenCombine error", e);
        }
    }

    public static void main(String[] args) {
        CompletableFutureTest test = new CompletableFutureTest();
        test.future();
        test.completableFutureRunAsync();
        test.completableFutureSupplyAsync();
        test.thenApply();
//        test.thenApplyException();
        test.exceptionApply();
        test.handleException();
        test.thenCombine();
    }
}
