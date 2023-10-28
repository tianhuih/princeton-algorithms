import java.util.LinkedList;
import java.util.Queue;

/**
 * A symbol table implemented with unordered singly linked-list
 */
public class SequentialSearchST<Key, Value> {

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node sentinel;
    private int size;

    public SequentialSearchST() {
        sentinel = new Node(null, null);
        size = 0;
    }

    // put key value pairs into the symbol table
    public void put(Key key, Value val) {
        checkKey(key);
        delete(key);
        // if val is null, no need to add new value
        if (val == null) return;
        Node newNode = new Node(key, val);
        Node next = sentinel.next;
        sentinel.next = newNode;
        newNode.next = next;
        size++;
    }

    // returns the value of associated key or null
    public Value get(Key key) {
        for (Node itr = sentinel.next; itr != null; itr = itr.next) {
            if (itr.key.equals(key)) return itr.val;
        }
        return null;
    }

    // delete the associated key
    public void delete(Key key) {
        checkKey(key);
        for (Node itr = sentinel; itr.next != null; itr = itr.next) {
            Node next = itr.next;
            if (next.key.equals(key)) {
                itr.next = next.next;
                size--;
                return;
            }
        }
    }

    private void checkKey(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null!");
        }
    }
    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return sentinel.next == null;
    }

    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new LinkedList<>();
        Node itr = sentinel;
        while (itr.next != null) {
            itr = itr.next;
            q.offer(itr.key);
        }
        return q;
    }

    public void print() {
        for (Node itr = sentinel.next; itr != null; itr = itr.next) {
            System.out.println(itr.key + " " + itr.val);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SequentialSearchST<String, Double> st = new SequentialSearchST<>();
        st.put("key1", 4.33);
        st.put("key2", 4.00);
        st.put("key3", 1.00);
        st.print();

        st.put("key1", 6.33);
        st.print();

        st.delete("key3");
        st.print();
    }
}
