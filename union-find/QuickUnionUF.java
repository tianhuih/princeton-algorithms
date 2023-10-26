import java.util.Scanner;

public class QuickUnionUF {

    private int[] parents;
    private int count;

    public QuickUnionUF(int size) {
        parents = new int[size];
        count = size;
        for (int i = 0; i < size; i++) {
            parents[i] = i;
        }
    }

    public void union(int p, int q) {
        validate(p);
        validate(q);
        int proot = find(p);
        int qroot = find(q);
        if (proot == qroot) return;
        parents[p] = qroot;
        count--;
    }

    public int find(int p) {
        validate(p);
        while (parents[p] != p) {
            p = parents[p];
        }
        return p;
    }

    public boolean isConnected(int p, int q) {
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    public int count() {
        return count;
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
        QuickUnionUF uf = new QuickUnionUF(n);
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
