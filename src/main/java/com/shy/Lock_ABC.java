package com.shy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shy on 2021/6/6.
 */
public class Lock_ABC {
    private int num;   // 当前状态值：保证三个线程之间交替打印
    private Lock lock = new ReentrantLock();
    private int maxNUm=10;
    private Object lockObj=new Object();
    private void printABC(int targetNum) {
        for (int i = 0; i < 10; ) {
            lock.lock();
            if (num % 3 == targetNum) {
                num++;
                i++;
                System.out.println("线程"+Thread.currentThread().getName()+":"+i);
            }
            lock.unlock();
        }
    }

    public void print123(int tragetNum){
        while(true){
            synchronized (lockObj){

                while(num%3!=tragetNum){
                    if(num>maxNUm){
                        break;
                    }
                    try {
                        lockObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(num>maxNUm){
                    break;
                }
                System.out.println(Thread.currentThread().getName()+":"+num++);
                lockObj.notifyAll();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Lock_ABC lockABC = new Lock_ABC();
        new Thread(() -> {
            lockABC.print123(0);
        }, "1").start();
        //TimeUnit.SECONDS.sleep(5);
        new Thread(() -> {
            lockABC.print123(1);
        }, "2").start();

        new Thread(() -> {
            lockABC.print123(2);
        }, "3").start();
    }
}
