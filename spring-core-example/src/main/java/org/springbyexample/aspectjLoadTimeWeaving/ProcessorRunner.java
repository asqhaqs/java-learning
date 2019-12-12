package org.springbyexample.aspectjLoadTimeWeaving;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-12-12
 */

public class ProcessorRunner {
    final Logger logger = LoggerFactory.getLogger(ProcessorRunner.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aspectJ-context.xml");
        Processer processer = new Processer();
        processer.process();
    }
}
