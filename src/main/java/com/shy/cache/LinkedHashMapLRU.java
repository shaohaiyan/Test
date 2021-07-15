package com.shy.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shy on 2021/6/4.
 */
public class LinkedHashMapLRU<K,V> extends LinkedHashMap{

    public static void main(String[] args){
        LinkedHashMapLRU<String,String> lru=new LinkedHashMapLRU<>(3);
        lru.put("1","1");
        lru.put("2","2");
        lru.put("3","3");
        System.out.println(lru.toString());
        lru.get("1");
        System.out.println(lru.toString());
        lru.put("4","4");
        System.out.println(lru.toString());
    }
    private int maxSize;
    private static float loadFactor=0.75f;

    public LinkedHashMapLRU(int maxSize){
        super(maxSize, loadFactor,true);
        this.maxSize=maxSize;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry eldes){
        return size()>maxSize;
    }
}
