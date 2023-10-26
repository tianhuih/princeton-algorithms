import java.util.Scanner;

public class QuickFindUF {

    private int[] id;    // id[i] = unique identifier of i
    private int count;   // count = number of components

    // constructor with parameter size
    public QuickFindUF(int size) {
        this.id = new int[size];
        this.count = size;
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    // connect two elements
    public void union(int p, int q) {
        validate(p);
        validate(q);
        if (id[p] == id[q]) return;

        // assign all elements in p to group q
        int oldId = id[p];
        for (int i = 0; i < this.count(); i++) {
            if (id[i] == oldId) {
                id[i] = id[q];
            }
        }
        count--;
    }

    // return the unique identifier of an element
    public int find(int p) {
        validate(p);
        return id[p];
    }

    // returns true if two elements are connected
    public boolean isConnected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }

    // returns the number of connected components
    public int count() {
        return count;
    }

    // validate that p is within the range of id
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        QuickFindUF uf = new QuickFindUF(n);
        while (sc.hasNextInt()) {
            int p = sc.nextInt();
            int q = sc.nextInt();
            if (uf.isConnected(p, q)) continue;
            uf.union(p, q);
            System.out.println("connected " + p + " and " + q);
        }
        System.out.println("total components: " + uf.count());
        sc.close();
    }
}