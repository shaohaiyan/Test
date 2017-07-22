package com.shy.test;

import java.io.*;

/**
 * 测试Externalizable
 * Created by haiyanshao on 2017/7/22.
 */
public class Blips {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("开始构造对象");
        Blip2 blip2 = new Blip2();
        Blip1 blip1 = new Blip1();
        ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream("Blips.out"));
        System.out.println("开始保存对象");
        output.writeObject(blip1);
        output.writeObject(blip2);
        output.close();
        ObjectInputStream in=new ObjectInputStream(new FileInputStream("Blips.out"));
        Blip1 blip11= (Blip1) in.readObject();
        Blip2 blip21= (Blip2) in.readObject();
    }


}

class Blip1 implements Externalizable {

    public Blip1() {
        System.out.println("Blip1 Construct");

    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip1 writeExternal");
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip1 readExternal");
    }
}

class Blip2 implements Externalizable {
    Blip2() {
        System.out.println("Blip2 Construct");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip2 writeExternal");
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip2 readExternal");
    }
}
