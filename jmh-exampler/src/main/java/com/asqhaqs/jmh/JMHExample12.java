package com.asqhaqs.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.log;
import static java.lang.Math.PI;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class JMHExample12 {

    /**
     *  测试jvm编译优化
     */

//    @Benchmark
//    public void test1(){
//
//    }
//
//    // 禁止 编译器优化 Dead Code
//    @CompilerControl(CompilerControl.Mode.EXCLUDE)
//    @Benchmark
//    public void test2(){
//        log(PI);
//    }
//
//    // 不是 Dead Code
//    @Benchmark
//    public double measureLog3(){
//        return Math.log(PI);
//    }
//
    double x1 = PI;
    double x2 = PI * 2;

    @Benchmark
    public double baseline(){
        return Math.pow(x1, 2);
    }

    @Benchmark
    public double powButReturnOne(){
        Math.pow(x1, 2);
        return Math.pow(x2, 2);
    }

    @Benchmark
    public double powThenAdd(){
        return Math.pow(x1, 2) + Math.pow(x2, 2);
    }

    // 使用 blackhole 来拉平 编译优化的开销 （都不进行优化， 但是blackhole 也会消耗资源）确保无返回值时候 DC 的发生不会影响基准测试
    @Benchmark
    public void useBlackHole(Blackhole hole){
        hole.consume(Math.pow(x1, 2));
        hole.consume(Math.pow(x2, 2));
    }


    //test
    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(JMHExample12.class.getSimpleName()).build();
        new Runner(opts).run();
    }
}
