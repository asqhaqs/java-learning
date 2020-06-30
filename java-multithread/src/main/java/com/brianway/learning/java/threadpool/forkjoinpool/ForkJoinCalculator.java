package com.brianway.learning.java.threadpool.forkjoinpool;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2020-06-29
 */
public class ForkJoinCalculator implements Calculator{
    private ForkJoinPool pool;


    private static class SunTask extends RecursiveTask<Long>{

        private long[] numbers;
        private int from;
        private int to;

        public SunTask(long[] numbers, int from, int to){
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute(){
            if(to - from < 6){
                long total = 0;
                for(int i = from; i <= to; i++){
                    total += numbers[i];
                }
                return total;
            }else{
                int middle = (from + to) / 2;
                SunTask taskLeft = new SunTask(numbers, from, middle);
                SunTask taskRight = new SunTask(numbers, middle + 1, to);
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }

        }

    }

    public ForkJoinCalculator(){
        pool = ForkJoinPool.commonPool();
    }


    @Override
    public long sunUp(long[] numbers) {
        Long result = pool.invoke(new SunTask(numbers, 0, numbers.length - 1));
        pool.shutdown();
        return result;
    }


    public static void main(String[] args) {
        ForkJoinCalculator fjc = new ForkJoinCalculator();
        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();
        Instant start = Instant.now();
        long result1 = fjc.sunUp(numbers);
        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis() + "ms");
        System.out.println("结果为：" + result1);





        Instant start1 = Instant.now();
        long result = LongStream.rangeClosed(0, 10000000L).parallel().reduce(0, Long::sum);
        Instant end1 = Instant.now();
        System.out.println("耗时：" + Duration.between(start1, end1).toMillis() + "ms");
        System.out.println("结果为：" + result);
    }


}
