import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    // Inner class for Doubly Linked List Node
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node> cache;
    // Dummy head (MRU side) and tail (LRU side) nodes
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy nodes
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        
        // Connect head and tail
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        // Move accessed node to head (MRU)
        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            // Update existing node
            node.value = value;
            moveToHead(node);
        } else {
            // Create new node
            // Check if adding this new node will exceed capacity and cause an eviction
            if (cache.size() == capacity) {
                // Peek at the LRU item before it gets removed
                Node lruCandidate = tail.prev; 
                System.out.println("WARNING: Cache is full. Adding new item '" + key + ":" + value + "' will cause eviction of LRU item '" + lruCandidate.key + ":" + lruCandidate.value + "'.");
            }

            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);

            if (cache.size() > capacity) {
                // Evict LRU (node before tail)
                Node lru = popTail();
                cache.remove(lru.key);
                // System.out.println("         Evicted LRU item: " + lru.key + ":" + lru.value); // This can be removed or kept, as the warning already covers it
            }
        }
    }

    /**
     * Adds a new node right after the head.
     */
    private void addNode(Node node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    /**
     * Remove an existing node from the linked list.
     */
    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    /**
     * Move an existing node to the head.
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    /**
     * Pop the current tail (LRU node).
     */
    private Node popTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }

    /**
     * Helper method to print the cache state for verification.
     * Iterates from Head (MRU) to Tail (LRU).
     */
    public void printCache() {
        Node current = head.next;
        boolean isFirst = true;
        boolean isLast = false;
        
        // We need to determine the last real element for formatting
        Node check = head.next;
        int count = 0;
        while(check != tail) {
            count++;
            check = check.next;
        }

        int index = 0;
        while (current != tail) {
            System.out.println(current.key + ":" + current.value);
            if (index == 0) {
                System.out.println("(most recently used block)");
            } else if (index == count - 1) {
                System.out.println("(least recently used block)");
            }
            current = current.next;
            index++;
        }
    }
}
