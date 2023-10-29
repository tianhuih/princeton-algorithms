import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * a symbol table implemented with ordered array and binary search algorithm
 *
 * the symbol table is ordered, with APIs including
 * min(), max(), floor(Key key), ceiling(Key key), rank(Key key) etc.
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private static final int INIT_SIZE = 8;
    private Key[] keys;
    private Value[] values;
    private int n;  // n = number of elements

    public BinarySearchST() {
        this(INIT_SIZE);
    }

    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
        n = 0;
    }

    // resize keys and values arr to newSize
    private void resize(int newSize) {
        Key[] newKeys = (Key[]) new Comparable[newSize];
        Value[] newValues = (Value[]) new Object[newSize];
        for (int i = 0; i < n; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys;
        values = newValues;
    }

    // throws exception if key is null
    private void checkKeyNotNull(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null!");
        }
    }

    // put key value pair into table
    public void put(Key key, Value val) {
        checkKeyNotNull(key);
        int ind = rank(key);
        // if key already exists, update value
        if (ind < n && keys[ind].equals(key)) {
            values[ind] = val;
            return;
        }
        // move all elements greater than key one position to the right
        if (n == keys.length) resize(keys.length * 2);
        for (int i = n - 1; i >= ind; i--) {
            keys[i + 1] = keys[i];
            values[i + 1] = values[i];
        }
        // put current key to its sorted position
        keys[ind] = key;
        values[ind] = val;
        n++;
    }

    // returns value associated with key or null
    public Value get(Key key) {
        checkKeyNotNull(key);
        int ind = rank(key);
        if (ind < n && keys[ind].equals(key)) return values[ind];
        return null;
    }

    // deletes key and its value
    public void delete(Key key) {
        checkKeyNotNull(key);
        int ind = rank(key);
        if (ind < n && keys[ind].equals(key)) {
            for (int i = ind + 1; i < n; i++) {
                keys[i - 1] = keys[i];
                values[i - 1] = values[i];
            }
            n--;
            keys[n] = null;
            values[n] = null;
            if (n > 0 && n <= keys.length / 4) resize(keys.length / 2);
        }
    }

    // returns true if contains key
    public boolean contains(Key key) {
        checkKeyNotNull(key);
        return get(key) != null;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("table is empty!");
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("table is empty!");
        return keys[n - 1];
    }

    // returns largest key less than or equal to key
    public Key floor(Key key) {
        checkKeyNotNull(key);
        int rank = rank(key);
        if (rank < n && keys[rank] == key) return keys[rank];
        return rank == 0 ? null : keys[rank - 1];
    }

    // returns smallest key greater than or equal to key
    public Key ceiling(Key key) {
        checkKeyNotNull(key);
        int rank = rank(key);
        return rank == n ? null : keys[rank];
    }

    // returns the number of elements less than key
    public int rank(Key key) {
        int l = 0, r = n - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int compareResult = keys[m].compareTo(key);
            if (compareResult > 0) {
                r = m - 1;
            } else if (compareResult < 0) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return l;
    }

    // returns key of rank k
    public Key select(int k) {
        if (k < 0 || k >= n) {
            throw new IllegalArgumentException("rank out of bound!");
        }
        return keys[k];
    }

    // deletes smallest key
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("table is empty!");
        delete(min());
    }

    // deletes largest key
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("table is empty!");
        delete(max());
    }

    // returns number of keys in [lo, hi]
    public int size(Key lo, Key hi) {
        checkKeyNotNull(lo);
        checkKeyNotNull(hi);
        if (lo.compareTo(hi) > 0) {
            throw new IllegalArgumentException("lo is greater than hi!");
        }
        return rank(hi) - rank(lo) + (contains(hi) ? 1 : 0);
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        checkKeyNotNull(lo);
        checkKeyNotNull(hi);
        if (lo.compareTo(hi) > 0) {
            throw new IllegalArgumentException("lo is greater than hi!");
        }
        int start = rank(ceiling(lo));
        int end = rank(floor(hi));
        Queue<Key> q = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            q.offer(keys[i]);
        }
        return q;
    }

    public Iterable<Key> keys() {
        return keys(keys[0], keys[n - 1]);
    }

    // print key-value pairs in the table
    private void print() {
        for (Key key : keys()) {
            System.out.println(key + " " + get(key));
        }
        System.out.println();
    }


    public static void main(String[] args) {
        BinarySearchST<String, Double> st = new BinarySearchST<>();
        st.put("key1", 4.33);
        st.put("key2", 4.00);
        st.put("key5", 1.00);
        st.print();
        System.out.println(st.floor("key0"));
        System.out.println(st.ceiling("key0"));
        System.out.println(st.ceiling("key5"));

        st.put("key1", 6.33);
        st.print();

        st.deleteMax();
        st.print();

        st.deleteMin();
        st.print();

        st.put("key1", 4.33);
        st.put("key3", 4.90);
        st.put("key5", 1.00);
        for (String k : st.keys("key0", "key8")) {
            System.out.println(k + " " + st.get(k));
        }
        System.out.println();
    }
}
