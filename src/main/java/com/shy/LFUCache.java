import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 1.
 */
class LFUCache {
    private Map<Integer, Node> key2Value;
    private Map<Integer, LinkedHashSet<Node>> frequent2Nodes;
    private int max;
    private int minFrequent = 1;

    public LFUCache(int capacity) {
        max = capacity;
        key2Value = new HashMap<>(capacity);
        frequent2Nodes = new HashMap<>();
    }

    private void increa(Node cur) {
        int preFre = cur.frequent;

        if (preFre != 0) {
            LinkedHashSet<Node> temp = frequent2Nodes.get(preFre);
            temp.remove(cur);
            if (preFre == minFrequent && temp.size() == 0) {
                minFrequent = preFre + 1;
            }
        }
        cur.frequent = preFre + 1;

        LinkedHashSet<Node> temp2 = frequent2Nodes.get(cur.frequent);
        if (temp2 == null) {
            temp2 = new LinkedHashSet();
            frequent2Nodes.put(cur.frequent, temp2);
        }
        temp2.add(cur);
    }

    public int get(int key) {
        Node cur = key2Value.get(key);
        if (cur == null) {
            return -1;
        }
        increa(cur);
        return cur.value;
    }

    public void put(int key, int value) {
        //Node node=new Node(key,value);
        if (max == 0) {
            return;
        }

        Node node = key2Value.get(key);
        if (node != null) {
            node.value = value;
            increa(node);

        } else {
            if (key2Value.size() >= max) {
                LinkedHashSet<Node> nodes = frequent2Nodes.get(minFrequent);
                if (nodes != null && !nodes.isEmpty()) {
                    Node needRemove = nodes.iterator().next();
                    nodes.remove(needRemove);
                    key2Value.remove(needRemove.key);
                }

            }
            node = new Node(key, value);
            key2Value.put(key, node);
            addNode(node);
        }


    }

    private void addNode(Node node) {
        node.frequent = 1;
        LinkedHashSet<Node> set = frequent2Nodes.get(1);
        if (set == null) {
            set = new LinkedHashSet<>();
            frequent2Nodes.put(1, set);
        }
        set.add(node);
        minFrequent = 1;
    }

    static class Node {
        private int frequent;
        private int key;
        private int value;

        Node(int k, int v) {
            key = k;
            value = v;
        }

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */