package org.springbyexample.di.xml;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-12-12
 */
public class ConstructorMessage {
    private String message = null;

    public ConstructorMessage(){

    }

    public ConstructorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
