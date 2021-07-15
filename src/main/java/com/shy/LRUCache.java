package com.shy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2021/6/24.
 */


//leetcode submit region begin(Prohibit modification and deletion)
public class LRUCache {
    private Map<Integer,SimpleEntry> map;
    private int maxSize;
    private SimpleEntry tail;
    private SimpleEntry head;
    public static void main(String[] args){
        LRUCache cache=new LRUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        cache.get(1);
        cache.put(3,3);
        cache.get(2);
        cache.put(4,4);
        cache.get(1);
        cache.get(3);
        cache.get(4);

    }
    public LRUCache(int capacity) {
        maxSize=capacity;
        map=new HashMap<>(capacity);
        head=new SimpleEntry(-1,-1);
        tail=new SimpleEntry(-1,-1);
        head.next=tail;
        tail.pre=head;
    }

    public int get(int key) {
        //返回值
        SimpleEntry entry=map.get(key);
        if(entry==null){
            return -1;
        }
        //修改顺序，先删除，并且放到队尾
        removeNode(entry);
        add2Tail(entry);
        return entry.value;
    }

    private void removeNode(SimpleEntry cur){
        if(cur==null||cur==tail){
            return;
        }
        SimpleEntry pre=cur.pre;
        pre.next=cur.next;
        cur.next.pre=pre;
        cur.next=null;
        cur.pre=null;
    }

    private void add2Tail(SimpleEntry cur){
        SimpleEntry pre=tail.pre;
        pre.next=cur;
        cur.pre=pre;
        cur.next=tail;
        tail.pre=cur;
    }

    public void put(int key, int value) {
        SimpleEntry newEntry=new SimpleEntry(key,value);
        SimpleEntry preEntry=map.put(key,newEntry);

        if(preEntry!=null){
            removeNode(preEntry);
        }

        add2Tail(newEntry);
        if(map.size()>maxSize){
            map.remove(head.next.key);
            removeNode(head.next);
        }
    }

    static class SimpleEntry{
        private int value;
        private int key;
        private SimpleEntry pre;
        private SimpleEntry next;
        SimpleEntry (int keyParam,int valueParam){
            key=keyParam;
            value=valueParam;
        }
    }
}

