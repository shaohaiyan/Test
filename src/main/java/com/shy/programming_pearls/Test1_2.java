package com.shy.programming_pearls;

/**
 * 第一章的第二个练习题
 * 通过位运算来表示位向量：类似于用位运算来表示加减乘除
 *
 *
 * Created by haiyanshao on 2017/11/21.
 */
public class Test1_2 {
    public static  final  int BITSPERWORD=32 ; //整型占用32位
    public static final int SHIFT=5; //2^5=32
    public static  final int MASK=0x1F; //用来求余数的
    public static final int N=10000000; //最大位数
    public static final int[] a=new int[1+N/BITSPERWORD];
    //固定的某一位置1,从右往左
    public static  void set(int i){
        a[i>>SHIFT] |= (1<<(i&MASK));
    }
    //将某一位置为0，从右往左
    public static  void clear(int i){
        a[i>>SHIFT] &= ~(1<<(i&MASK));
    }
    //检测第i位是0还是1
    public static  int test(int i){
        int s=(1<<(i&MASK));
        return (a[i>>SHIFT]&(1<<(i&MASK)))>>(i&MASK);
    }
    public static void main(String[] args){
        set(10001);
        System.out.println(test(1000));

    }
}
