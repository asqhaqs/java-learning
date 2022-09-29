package com.brianway.learning.java.limiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import static java.lang.Thread.currentThread;

/**
 * 漏桶实现， 对流入速率不做限制，流出速率限制， 和令牌桶不一样（对流入速率限制）
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2021-05-03
 */
public class RateLimiterBucket {
    static class Request {
        private final int data;
        public Request(int data) {
            this.data = data;
        }

        public int getData() {
            return this.data;
        }

        @Override
        public String toString() {
            return "Request(" + "data=" + data + ")";
        }
    }

    private final ConcurrentLinkedQueue<Request> bucket = new ConcurrentLinkedQueue<>();
    private final static int BUCKET_CAPACITY = 1000;
    private final RateLimiter rateLimiter = RateLimiter.create(10.0D);
    private final Monitor requestMonitor = new Monitor();
    private final Monitor handleMonitor = new Monitor();

    public void submitRequest(int data) {
        this.submitRequest(new Request(data));
    }

    public void submitRequest(Request request) {
        if (requestMonitor.enterIf(requestMonitor.newGuard(() -> bucket.size() < BUCKET_CAPACITY))) {
            try {
                boolean result = bucket.offer(request);
                if (result) {
                    System.out.println(currentThread() + " submit request: " + request.getData() + " successful");
                } else {
                    // produce into MQ and try again later.
                }
            } finally {
                requestMonitor.leave();
            }
        } else {
            // 漏桶溢出 降权处理
            System.out.println("The request:" + request.getData() + " will be down-dimensional handle due to bucket is overflow");
            // produce into MQ and try again later.
        }
    }

    public void handleRequest(Consumer<Request> consumer) {
        // 若漏桶存在请求，则处理
        if (handleMonitor.enterIf(handleMonitor.newGuard(() -> !bucket.isEmpty()))) {
            try {
                // 匀速处理
                rateLimiter.acquire();
                consumer.accept(bucket.poll());
            } finally {
                handleMonitor.leave();
            }
        }
    }

    public static void main(String[] args) {
        AtomicInteger data = new AtomicInteger(0);
        RateLimiterBucket bucket = new RateLimiterBucket();
        for(int i = 0; i < 10; i++) {
            new Thread( () -> {
                while (true) {
                    bucket.submitRequest(data.getAndIncrement());
                    try {
                        TimeUnit.MICROSECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    bucket.handleRequest(System.out::println);
                }
            }).start();
        }
    }
}
