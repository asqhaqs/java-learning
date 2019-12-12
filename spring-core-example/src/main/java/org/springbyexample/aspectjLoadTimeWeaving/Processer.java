package org.springbyexample.aspectjLoadTimeWeaving;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-12-12
 */
public class Processer {
    final Logger logger = LoggerFactory.getLogger(Processer.class);

    protected int counter = 0;

    public void process(){
        counter++;
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            logger.error(e.getMessage(), e);
        }

        logger.debug("counter=" + counter);
    }

}
