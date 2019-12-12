package org.di.xml;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springbyexample.di.xml.SetterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-12-12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context.xml"})
public class SetterMessageTest {
    final Logger logger = LoggerFactory.getLogger(SetterMessageTest.class);

    @Autowired
    private SetterMessage message = null;

    @Test
    public void testMessage(){
        assertNotNull("Setter message instance is null", message);
        String msg = this.message.getMessage();
        assertNotNull("Message is null.", msg);
        String expectedMessage = "spring is suck";
        assertEquals("Message should be '" + expectedMessage + "'.", expectedMessage, msg);
        logger.info("message is {}", msg);
    }
}
