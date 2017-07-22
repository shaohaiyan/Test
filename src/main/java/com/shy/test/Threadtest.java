package com.shy.test;

import java.util.concurrent.TimeUnit;

/**
 * Created by haiyanshao on 2017/7/16.
 */
public class Threadtest {

    private static boolean isStop=false;
    public static void main(String[] args){
        new Thread(new Runnable() {
            public void run() {
                int i=0;
                while(!isStop){
                    i++;
                }
                System.out.println("i="+i);
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isStop=true;
        System.out.println("isStop="+isStop);

    }
}
