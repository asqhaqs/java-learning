package com.brianway.learning.java8.streamapi.parallel;

import java.util.function.Function;
import java.util.stream.LongStream;

public class ParallelStreamCount {

    public static void main(String[] args) {
        System.out.println("Sequential sum done in: " + measureSumPerf(ParallelStreams::sequentialSum, 10000000) + " msecs");
        System.out.println("Iterative sum done in:" +
                measureSumPerf(ParallelStreams::iterativeSum, 10_000_000) + " msecs");
        System.out.println("LongStream sum done in: " + rangedSum(10_000_000) + " msecs");
    }

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    // 省去了装箱拆箱
    public static long rangedSum(long n) {
        long fastest = Long.MAX_VALUE;
        long start = System.nanoTime();
        long sum = LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Result: " + sum);
        if (duration < fastest) fastest = duration;
        return fastest;
    }
}
