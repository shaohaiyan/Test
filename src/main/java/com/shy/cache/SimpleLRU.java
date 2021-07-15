package com.shy.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2021/6/4.
 */
public class SimpleLRU {
    private int maxSize;
    private Map<String, SimpleNode> key2Node;
    private SimpleNode head;
    private SimpleNode tail;

    SimpleLRU(int size) {
        this.maxSize = size;
        key2Node = new HashMap<>(size);
    }

    public String get(String key) {
        SimpleNode valueNode = key2Node.get(key);
        changePostion(valueNode);
        return valueNode == null ? null : valueNode.value;
    }

    private void changePostion(SimpleNode valueNode) {
        removeNode(valueNode);
        add2Tail(valueNode);
    }

    public void put(String key, String value) {
        SimpleNode valueNode = new SimpleNode(key, value);
        SimpleNode preValue = key2Node.put(key, valueNode);
        //注意这里
        if (preValue != null) {
            removeNode(preValue);
        }
        if (head == null) {
            head = new SimpleNode("head", "");
            tail = new SimpleNode("tail", "");
            head.next = tail;
            tail.pre = head;
        }
        add2Tail(valueNode);
        if (key2Node.size() > maxSize) {
            //删除队头元素
            SimpleNode deleteNode = head.next;
            removeNode(deleteNode);
            key2Node.remove(deleteNode.key);
        }
    }

    private void add2Tail(SimpleNode newValue){
        SimpleNode last=tail.pre;
        last.next=newValue;
        newValue.pre=last;
        newValue.next=tail;
        tail.pre=newValue;
    }

    private void removeNode(SimpleNode preValue) {
        if(preValue==head||preValue==tail){
            return;
        }
        SimpleNode pre = preValue.pre;
        SimpleNode next = preValue.next;
        pre.next = next;
        next.pre = pre;
        preValue.next = null;
        preValue.pre = null;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        SimpleNode pre = null;
        SimpleNode cur = head;
        while (cur != pre) {
            res.append(cur.key).append(",");
            cur = cur.next;
        }
        return res.toString();
    }
}

class SimpleNode {
    String value;
    String key;
    SimpleNode pre;
    SimpleNode next;

    SimpleNode(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
