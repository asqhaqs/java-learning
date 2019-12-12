package org.springbyexample.aspectjLoadTimeWeaving;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-12-12
 */
@Aspect
public class PerformanceAdvice {
    @Pointcut("execution(public * org.springbyexample.aspectjLoadTimeWeaving..*.*(..))")
    public void aspectjLoadTimeWeavingExamples(){

    }

    @Around("aspectjLoadTimeWeavingExamples()")
    public Object profile(ProceedingJoinPoint pjp)throws Throwable{
        final Logger logger = LoggerFactory.getLogger(pjp.getSignature().getDeclaringType());
        StopWatch sw = new StopWatch(getClass().getSimpleName());
        try{
            sw.start(pjp.getSignature().getName());
            return pjp.proceed();
        }finally{
            sw.stop();
            logger.info(sw.prettyPrint());
        }
    }
}
