package com.shy;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * Created by shy on 2021/6/7.
 */
public class Main {
    private static int num = 0;
    private Object lock = new Object();

    public static void main(String[] args) {
        Main main=new Main();
        new Thread(main.new Consumer()).start();
        new Thread(main.new Producer()).start();
        new Thread(main.new Producer()).start();
        new Thread(main.new Producer()).start();
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (num == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("消费线程:" + Thread.currentThread().getName()+":" + (--num));
                    lock.notifyAll();
                }
            }

        }
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (num == 3) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("生产线程:" + Thread.currentThread().getName() + ":" + (++num));
                    lock.notifyAll();
                }
            }
        }
    }
}


