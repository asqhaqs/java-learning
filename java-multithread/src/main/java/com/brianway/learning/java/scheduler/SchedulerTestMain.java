package com.brianway.learning.java.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2022-11-12
 */
@Slf4j
public class SchedulerTestMain {
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        SchedulerTestMain schedulerTestMain = new SchedulerTestMain();
        schedulerTestMain.scheduledExecutorService
                .scheduleAtFixedRate(SchedulerTestMain::testRun, 5, 5, TimeUnit.SECONDS);
    }

    public static void testRun() {
        log.warn("test scheduler task");
    }
}
