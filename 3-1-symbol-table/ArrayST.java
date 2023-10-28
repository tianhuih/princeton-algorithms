import java.util.LinkedList;
import java.util.Queue;

/**
 * a symbol table (map) implemented with unordered array
 */
public class ArrayST<Key, Value> {

    private static final int INIT_SIZE = 8;
    private Key[] keys;
    private Value[] values;
    private int size;

    // constructor
    public ArrayST() {
        keys = (Key[]) new Object[INIT_SIZE];
        values = (Value[]) new Object[INIT_SIZE];
        size = 0;
    }

    // resize keys and values arr to newSize
    private void resize(int newSize) {
        Key[] newKeys = (Key[]) new Object[newSize];
        Value[] newValues = (Value[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys;
        values = newValues;
    }

    // put the key value into table 0(N)
    public void put(Key key, Value val) {
        // deal with duplicates
        delete(key);

        // resize when necessary
        if (size >= keys.length) {
            resize(keys.length * 2);
        }

        // put data to the end of arrays
        keys[size] = key;
        values[size] = val;
        size++;
    }

    // get value associated with input key 0(N)
    public Value get(Key key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return null;
    }

    // delete the key O(N)
    public void delete(Key key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                // move last element to the deleted position
                keys[i] = keys[size - 1];
                values[i] = values[size - 1];
                keys[size - 1] = null;
                values[size - 1] = null;
                size--;
                // resize when necessary
                if (size > 0 && size == keys.length / 4) resize(keys.length / 2);
                return;
            }
        }
    }

    // true if the table contains input key
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // true if the table is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // returns the number of elements in the table
    public int size() {
        return size;
    }

    // returns an iterable object containing all keys in the table
    public Iterable<Key> keys() {
        Queue<Key> q = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            q.add(keys[i]);
        }
        return q;
    }

    // print key-value pairs in the table
    private void print() {
        for (Key key : keys()) {
            System.out.println(key + " " + get(key));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayST<String, Double> st = new ArrayST<>();
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
