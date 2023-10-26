import java.util.Scanner;

public class WeightedQuickUnionPathCompression {

    private int[] parents;
    private int[] size;
    private int count;

    public WeightedQuickUnionPathCompression(int n) {
        parents = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parents[i] = i;
            size[i] = 1;
        }
    }

    public void union(int p, int q) {
        validate(p);
        validate(q);
        int proot = find(p);
        int qroot = find(q);
        if (proot == qroot) return;

        // connect the smaller tree to the larger tree
        if (size[proot] >= size[qroot]) {
            parents[qroot] = proot;
            size[proot] += size[qroot];
        } else {
            parents[proot] = qroot;
            size[qroot] += size[proot];
        }
        count--;
    }


    public int find(int p) {
        validate(p);
        int root = p;
        while (parents[root] != root) {
            root = parents[root];
        }
        while (parents[p] != p) {
            int next = parents[p];
            parents[p] = root;
            p = next;
        }
        return root;
    }

    public boolean isConnected(int p, int q) {
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }


    public int size(int p) {
        validate(p);
        return size[p];
    }

    private void validate(int p) {
        int n = parents.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException(p + " is not in range 0 and " + (n - 1));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(n);
        System.out.println("initialized UF with size " + n);
        sc.nextLine();

        while (sc.hasNextLine()) {
            String[] commands = sc.nextLine().split(" ");
            validateArgs(commands);
            switch (commands[0]) {
                case "union":
                    int p = Integer.parseInt(commands[1]);
                    int q = Integer.parseInt(commands[2]);
                    uf.union(p, q);
                    System.out.println("connected " + p + " and " + q);
                    break;
                case "find":
                    System.out.println(uf.find(Integer.parseInt(commands[1])));
                    break;
                case "connected":
                    System.out.println(uf.isConnected(
                        Integer.parseInt(commands[1]),
                        Integer.parseInt(commands[2])));
                    break;
                case "count":
                    System.out.println(uf.count());
                    break;
                case "size":
                    System.out.println(uf.size(Integer.parseInt(commands[1])));
                    break;
                case "exit":
                    return;
                default:
                    break;
            }
        }
        sc.close();
    }

    private static void validateArgs(String[] commands) {
        switch (commands[0]) {
            case "union":
                validateIntegers(commands, 2);
                break;
            case "find":
                validateIntegers(commands, 1);
                break;
            case "connected":
                validateIntegers(commands, 2);
                break;
            case "count":
                validateIntegers(commands, 0);
                break;
            case "size":
                validateIntegers(commands, 1);
            case "exit":
                break;
            default:
                throw new IllegalArgumentException("command not found");
        }
    }

    // number = number of integers to validate in the command
    private static void validateIntegers(String[] commands, int number) {
        if (commands.length != number + 1) {
            throw new IllegalArgumentException("invalid argument: size != 3");
        }
        for (int i = 0; i < number; i++) {
            try {
                Integer.parseInt(commands[1 + i]);
            } catch (Exception e) {
                throw new IllegalArgumentException("element " + (i + 1) + " is not an integer!");
            }
        }
    }
}
