package com.asqhaqs.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JHMExample17 {
    // Inc1 和 In2 实现完全一样
    interface Inc{
        int inc();
    }

    public static class Inc1 implements Inc {
        private int i = 0;
        @Override
        public int inc(){
            return ++i;
        }
    }

    public static class Inc2 implements Inc {
        private int i = 0;

        @Override
        public int inc(){
            return ++i;
        }
    }

    private Inc inc1 = new Inc1();
    private Inc inc2 = new Inc2();

    private int measure(Inc inc){
        int result = 0;
        for(int i = 0; i < 10; i++){
            result +=inc.inc();
        }
        return result;
    }

    @Benchmark
    public int measure_inc_1(){
        return this.measure(inc1);
    }

    @Benchmark
    public int measure_inc_2(){
        return this.measure(inc2);
    }

    @Benchmark
    public int measure_inc_3(){
        return this.measure(inc1);
    }

    //fork 让多个函数执行之间没有干扰，
    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(JHMExample17.class.getSimpleName()).build();
        new Runner(opts).run();
    }

}
