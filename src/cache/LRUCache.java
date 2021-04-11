package cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    int max_capacity;
    final Node head = new Node();
    final Node tail = new Node();
    Map<Integer, Node> nodeMap;

    public LRUCache(int max_capacity) {
        this.max_capacity = max_capacity;
        nodeMap = new HashMap<>();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = nodeMap.get(key);
        int result = -1;

        if (node != null) {
            result = node.value;
            remove(node);
            add(node);
        }
        return result;
    }

    public void put(int key, int value) {
        Node newNode = nodeMap.get(key);
        if (newNode != null) {
            remove(newNode);
            newNode.value = value;
            add(newNode);
            return;
        }
        if (nodeMap.size() == max_capacity) {
            nodeMap.remove(tail.prev.key);
            remove(tail.prev);
        }
        newNode = new Node();
        newNode.key = key;
        newNode.value = value;
        add(newNode);
        nodeMap.put(key, newNode);
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void add(Node newNode) {
        Node oldNode = head.next;
        head.next = newNode;
        newNode.prev = head;
        newNode.next = oldNode;
        oldNode.prev = newNode;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        
    }

    public static class Node {
        int key;
        int value;
        Node prev;
        Node next;
    }

}
