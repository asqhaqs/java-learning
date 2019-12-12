package org.springbyexample.di.runner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springbyexample.di.xml.DefaultMessage;
import org.springbyexample.di.xml.SetterMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-12-12
 */
public class MessageRunner {
    final static Logger logger = LoggerFactory.getLogger(MessageRunner.class);

    public static void main(String[] args) {
        logger.info("Initializing Spring context");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");

        logger.info("Spring context initialized");
        DefaultMessage message = (DefaultMessage) applicationContext.getBean("message");
        SetterMessage message1 = (SetterMessage) applicationContext.getBean("setter_message");

        logger.info("message= " + message.getMessage() );
        logger.info("message1= " + message1.getMessage() );
    }
}
