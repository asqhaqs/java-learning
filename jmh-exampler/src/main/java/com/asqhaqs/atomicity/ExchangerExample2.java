package com.asqhaqs.atomicity;

import org.openjdk.jmh.annotations.Setup;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

public class ExchangerExample2 {
    public static void main(String[] args) throws InterruptedException {
        final Exchanger<String> exchanger = new Exchanger<>();
        StringGenerator generator = new StringGenerator(exchanger, "Generator");
        StringConsumer consumer = new StringConsumer(exchanger, "Consumer");
        consumer.start();
        generator.start();
        TimeUnit.SECONDS.sleep(60);
        consumer.closed();
        generator.closed();
    }

    private interface Closable{
        void close();
        boolean closed();
    }

    private abstract static class ClosableThread extends Thread implements Closable{
        protected final Exchanger<String> exchanger;
        private volatile boolean closed = false;

        private ClosableThread(Exchanger<String> exchanger, final String name){
            super(name);
            this.exchanger = exchanger;
        }

        @Override
        public void run(){
            while (!closed()) {
                this.doExchange();
            }
        }

        protected abstract void doExchange();

        @Override
        public void close(){
            System.out.println(currentThread() + " will be closed.");
            this.closed = true;
            this.interrupt();
        }

        @Override
        public boolean closed() {
            return this.closed || this.isInterrupted();
        }


    }

    private static class StringGenerator extends ClosableThread {
        private char initialChar = 'A';
        private StringGenerator (Exchanger<String> exchanger, String name) {
            super(exchanger, name);
        }

        @Override
        protected void doExchange() {
            String str = "";
            for (int i = 0; i < 3; i++) {
                randomSleep();
                str += (initialChar++);
            }

            try{
                if(!this.closed()) exchanger.exchange(str);
            }catch (InterruptedException e) {
                System.out.println(currentThread() + " received the close signal.");
            }
        }
    }

    private static class StringConsumer extends ClosableThread{

        private StringConsumer(Exchanger<String> exchanger, String name) {
            super(exchanger, name);
        }

        @Override
        protected void doExchange() {
            try {
                if(!this.closed()) {
                    String data = exchanger.exchange(null);
                    System.out.println("received the data: " + data);
                }
            }catch (InterruptedException e) {
                System.out.println(currentThread() + " received the close signal.");
            }
        }
    }


    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
