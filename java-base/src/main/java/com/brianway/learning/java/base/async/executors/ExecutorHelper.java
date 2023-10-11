package com.brianway.learning.java.base.async.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyu05 <liuyu05@kuaishou.com> 2022-06-08
 */
public class ExecutorHelper {

    private static final ThreadPoolExecutor EXECUTOR2 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));

    public static ThreadPoolExecutor getExecutor() {
        return EXECUTOR2;
    }

    public static ExecutorCompletionService getOrderedExecutor() {
        ExecutorCompletionService service = new ExecutorCompletionService(getExecutor());
        return service;
    }

    public static ExecutorContext submit(String name, Callable callable) {
        ExecutorContext context = ExecutorContext.init();
        Future future = getExecutor().submit(callable);
        context.put(name, future);
        return context;
    }

    public static void submit(String name, Callable callable, ExecutorContext context) {
        context.put(name, getExecutor().submit(callable));
    }

    public static void run(Runnable runnable) {
        getExecutor().submit(runnable);
    }

}
