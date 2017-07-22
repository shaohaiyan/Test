package com.shy.test;

/**
 * Created by haiyanshao on 2017/7/16.
 */
public class OutputThread implements Runnable {

    private int num;
    private Object lock;

    public OutputThread(int num, Object lock) {
        super();
        this.num = num;
        this.lock = lock;
    }

    public void run() {
        try {
            while(true){
                synchronized(lock){
                    lock.notifyAll();
                    System.out.println(num);
                    lock.wait();
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        final Object lock = new Object();

        Thread thread1 = new Thread(new OutputThread(1,lock));
        Thread thread2 = new Thread(new OutputThread(2, lock));
        //Thread thread2 = new Thread(new OutputThread(3, lock));

        thread1.start();
        thread2.start();
    }

}