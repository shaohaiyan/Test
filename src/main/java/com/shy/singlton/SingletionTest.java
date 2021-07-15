package com.shy.singlton;

/**
 * Created by shy on 2021/6/1.
 */
public class SingletionTest {
    private static volatile SingletionTest single;

    private SingletionTest(){

    }

    public static SingletionTest getInstance(){
        if(single==null){
            synchronized(Singletion.class){
                if(single!=null){
                    single=new SingletionTest();
                }
            }
        }
        return  single;
    }

    public Object readResolve(){
        return getInstance();
    }

    public static SingletionTest getInstance2(){
        return SingletionHandle.singletionTest;
    }

    public static synchronized SingletionTest getInstance3(){
        if(single==null){
            single=new SingletionTest();
        }
        return single;
    }

    private static class SingletionHandle{
        private static SingletionTest singletionTest=new SingletionTest();
    }
}
