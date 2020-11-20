package com.asqhaqs.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * arrayList and linkedList add time compare
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample01 {
    private final static String DATA = "DUMMY DATA";
    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Iteration)
    public void setUp(){
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();

    }

    @Benchmark
    public List<String> arrayListAdd(){
        this.arrayList.add(DATA);
        return arrayList;
    }

    @Benchmark
    public List<String> linkedListAdd(){
        this.linkedList.add(DATA);
        return this.linkedList;
    }

    /**
     * 抽样测试
     * @throws InterruptedException
     */
    @BenchmarkMode(Mode.SampleTime)
    @Benchmark
    public void testSampleTime() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(1);
    }

    /**
     * 冷启动测试
     * @param
     * @throws RunnerException
     */
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 0)
    @Benchmark
    public void testSingleShotTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(JMHExample01.class.getSimpleName())
                .forks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .build();
        new Runner(opts).run();
    }


}
