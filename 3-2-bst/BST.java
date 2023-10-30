import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BST<Key extends Comparable<Key>, Value> {

    private class Node {
        Key key;
        Value val;
        Node left;
        Node right;
        int n;  // node count of subtree rooted at the node

        public Node(Key key, Value val, int n) {
            this.key = key;
            this.val = val;
            this.n = n;
        }
    }

    private Node root;

    // empty constructor
    public BST() {
    }

    // throws exception if key is null
    private void checkKeyNotNull(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null!");
        }
    }

    // throws exception if the bst is empty
    private void checkEmpty() {
        if (isEmpty()) throw new NoSuchElementException("the bst is empty!");
    }

    // returns the node associated with the key or null
    private Node getNode(Key key, Node root) {
        checkKeyNotNull(key);
        if (root == null) return null;
        int cmp = root.key.compareTo(key);
        if (cmp == 0)       return root;
        else if (cmp > 0)   return getNode(key, root.left);
        else                return getNode(key, root.right);
    }

    // put key value pair into subtree rooted at node
    // returns the root node after insertion
    private Node putNode(Key key, Value val, Node node) {
        if (node == null) return new Node(key, val, 1);
        int cmp = node.key.compareTo(key);
        if (cmp == 0)        node.val = val;
        else if (cmp > 0)    node.left = putNode(key, val, node.left);
        else                 node.right = putNode(key, val, node.right);
        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    // put key value pair into the symbol table
    public void put(Key key, Value val) {
        checkKeyNotNull(key);
        if (val == null) {
            delete(key);
            return;
        }
        root = putNode(key, val, root);
    }

    // return value associated the the input key
    public Value get(Key key) {
        checkKeyNotNull(key);
        Node node = getNode(key, root);
        return node == null ? null : node.val;
    }

    // true if contains key
    public boolean contains(Key key) {
        checkKeyNotNull(key);
        return getNode(key, root) != null;
    }

    // true if the BST is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of elements in the BST
    public int size() {
        return size(root);
    }

    // return the size of subtree rooted at node
    private int size(Node node) {
        return node == null ? 0 : node.n;
    }

    // return the minimum(leftmost) key
    public Key min() {
        checkEmpty();
        return getMinNode(root).key;
    }

    // return min node in subtree rooted at node
    private Node getMinNode(Node node) {
        if (node == null) return null;
        while (node.left != null) node = node.left;
        return node;
    }

    // return the maximum(rightmost) key
    public Key max() {
        checkEmpty();
        return getMaxNode(root).key;
    }

    // return max node in subtree rooted at node
    private Node getMaxNode(Node node) {
        if (node == null) return null;
        while (node.right != null) node = node.right;
        return node;
    }
    // return the largest key less than or equal to input
    public Key floor(Key key) {
        checkKeyNotNull(key);
        return floor(key, root);
    }

    // recursively find the floor(key) in subtree rooted at node
    private Key floor(Key key, Node node) {
        checkKeyNotNull(key);
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if (cmp == 0)       return node.key;
        if (cmp > 0)        return floor(key, node.left);
        Key ans = floor(key, node.right);
        return ans == null ? node.key : ans;
    }

    // return the smallest key greater than or equal to input
    public Key ceiling(Key key) {
        checkKeyNotNull(key);
        return ceiling(key, root);
    }

    // find ceiling(key) in subtree rooted at node
    private Key ceiling(Key key, Node node) {
        checkKeyNotNull(key);
        if (node == null) return null;
        if (node.key.equals(key)) return node.key;
        if (node.key.compareTo(key) > 0) {
            Key ans = ceiling(key, node.left);
            return ans == null ? node.key : ans;
        } else {
            return ceiling(key, node.right);
        }
    }

    // return the key of rank k
    public Key select(int k) {
        if (root == null) return null;
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("rank is out of bound!");
        }
        return select(k, root);
    }

    // return the key of rank in subtree rooted at node
    private Key select(int k, Node node) {
        if (root == null) return null;
        int left = size(root.left);
        if (k < left) {
            return select(k, node.left);
        } else if (k == left) {
            return node.key;
        } else {
            return select(k - left - 1, node.right);
        }
    }


    // return the number of keys less than key
    public int rank(Key key) {
        checkKeyNotNull(key);
        return rank(key, root);
    }

    // return the rank of key in subtree rooted at node
    private int rank(Key key, Node node) {
        checkKeyNotNull(key);
        if (node == null) return 0;
        int cmp = node.key.compareTo(key);
        if (cmp == 0)       return size(node.left);
        if (cmp > 0)        return rank(key, node.left);
        else                return size(node.left) + 1 + rank(key, node.right);
    }

    // delete smallest key
    public void deleteMin() {
        checkEmpty();
        root = deleteMin(root);
    }

    // delete min in subtree rooted at node and return new root
    private Node deleteMin(Node node) {
        if (node == null) return null;
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    // delete largest key
    public void deleteMax() {
        checkEmpty();
        deleteMax(root);
    }

    // delete max in subtree rooted at node and return new root
    private Node deleteMax(Node node) {
        if (node == null) return null;
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    // delete key and its value
    public void delete(Key key) {
        checkKeyNotNull(key);
        root = delete(key, root);
    }

    // delete key in subtree rooted at node and return new root
    private Node delete(Key key, Node node) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if      (cmp > 0)   node.left = delete(key, node.left);
        else if (cmp < 0)   node.right = delete(key, node.right);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node next = getMinNode(node.right);
            next.right = deleteMin(node.right);
            next.left = node.left;
            node = next;
        }
        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    // returns the number of keys in [lo, hi]
    public int size(Key lo, Key hi) {
        checkKeyNotNull(lo);
        checkKeyNotNull(hi);
        if (lo.compareTo(hi) > 0) return 0;
        return rank(hi) - rank(lo) + (contains(hi) ? 1 : 0);
    }

    // return keys in range in sorted order
    public Iterable<Key> keys(Key lo, Key hi) {
        checkKeyNotNull(lo);
        checkKeyNotNull(hi);
        Queue<Key> q = new LinkedList<>();
        inorder(root, q, lo, hi);
        return q;
    }

    // return all keys in sorted order
    public Iterable<Key> keys() {
        Queue<Key> q = new LinkedList<>();
        inorder(root, q);
        return q;
    }

    // inorder traversal of BST and put keys into queue
    private void inorder(Node node, Queue<Key> q) {
        if (node == null) return;
        inorder(node.left, q);
        q.offer(node.key);
        inorder(node.right, q);
    }

    // inorder traversal of BST within range and put keys into queue
    private void inorder(Node node, Queue<Key> q, Key lo, Key hi) {
        if (node == null) return;
        if (node.left != null && getMaxNode(node.left).key.compareTo(lo) >= 0) {
            inorder(node.left, q);
        }
        if (node.key.compareTo(lo) >= 0 && node.key.compareTo(hi) <= 0) {
            q.offer(node.key);
        }
        if (node.right != null && getMinNode(node.right).key.compareTo(hi) <= 0) {
            inorder(node.right, q);
        }
    }

    public static void main(String[] args) {
        BST<String, Double> st = new BST<>();
        st.put("key1", 4.33);
        st.put("key2", 4.00);
        st.put("key5", 1.00);
        print(st);
        System.out.println(st.floor("key0"));
        System.out.println(st.ceiling("key0"));
        System.out.println(st.ceiling("key5"));

        st.put("key1", 6.33);
        print(st);

        st.deleteMax();
        print(st);

        st.deleteMin();
        print(st);

        st.put("akey", 4.33);
        st.put("key3", 4.90);
        st.put("key5", 1.00);
        print(st);

        for (String k : st.keys("key0", "key3")) {
            System.out.println(k + " " + st.get(k));
        }
        System.out.println();
    }

    private static void print(BST<String, Double> st) {
        for (String key : st.keys()) {
            System.out.println(key + " " + st.get(key));
        }
        System.out.println();
    }
}
