import java.util.LinkedList;
import java.util.Queue;

public class ArrayST<Key, Value> {

    private static final int INIT_SIZE = 8;
    private Key[] keys;
    private Value[] values;
    private int size;

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

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            q.add(keys[i]);
        }
        return q;
    }

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
