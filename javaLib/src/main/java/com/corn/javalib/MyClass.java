package com.corn.javalib;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("thread name:" + Thread.currentThread().getName() + " begin...");
        logUncaughtExceptionHandler();


        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println("thread name:" + Thread.currentThread().getName() + " uncaughtException...");
            }
        });

        //errorMethod();

        Thread thread = new Thread(new MyRunnable());
        thread.start();


        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("thread name:" + Thread.currentThread().getName() + " end...");
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("thread name:" + Thread.currentThread().getName() + " start run");
            logUncaughtExceptionHandler();

            errorMethod();

            System.out.println("thread name:" + Thread.currentThread().getName() + " end run");
        }
    }

    public static int errorMethod() {
        String name = null;

        return name.length();
    }


    public static void logUncaughtExceptionHandler(){
        Thread thread = Thread.currentThread();
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = thread.getUncaughtExceptionHandler();
        Thread.UncaughtExceptionHandler  defaultUncaughtExceptionHandler = thread.getDefaultUncaughtExceptionHandler();

        System.out.println("thread name:" + thread.getName() + " uncaughtExceptionHandler:" + uncaughtExceptionHandler
                            + " defaultUncaughtExceptionHandler:" + defaultUncaughtExceptionHandler);


    }
}
