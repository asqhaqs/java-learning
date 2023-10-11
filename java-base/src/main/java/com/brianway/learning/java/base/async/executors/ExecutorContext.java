package com.brianway.learning.java.base.async.executors;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author liuyu05 <liuyu05@kuaishou.com> 2022-06-08
 */
public class ExecutorContext {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorContext.class);

    private Map<String, Future> futureMap = Maps.newHashMap();

    public void put(String name, Future future) {
        futureMap.put(name, future);
    }

    public Object getResult(String name) {
        Future future = futureMap.get(name);
        try {
            logger.info("name {}, future {} {}", name, future, Objects.nonNull(future) ? future.isDone() : false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (future == null) {
            return null;
        }
        try {
            return future.get();
        } catch (Exception e) {
            //
        }
        return null;
    }

    public Object getResult(String name, long time, TimeUnit timeUnit) {
        Future future = futureMap.get(name);
        if (future == null) {
            return null;
        }
        try {
            return future.get(time, timeUnit);
        } catch (Exception e) {
            //
        }
        return null;
    }


    public static ExecutorContext init() {
        return new ExecutorContext();
    }

}
