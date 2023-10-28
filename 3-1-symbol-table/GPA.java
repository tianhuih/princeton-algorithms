import java.util.HashMap;
import java.util.Scanner;

public class GPA {
    public static void main(String[] args) {
        HashMap<String, Double> letterMapping = new HashMap<>();
        letterMapping.put("A+", 4.33);
        letterMapping.put("A", 4.00);
        letterMapping.put("A-", 3.67);
        letterMapping.put("B+", 3.33);
        letterMapping.put("B", 3.00);
        letterMapping.put("B-", 2.67);
        letterMapping.put("C+", 2.33);
        letterMapping.put("C", 2.00);
        letterMapping.put("C-", 1.67);
        letterMapping.put("D", 1.00);
        letterMapping.put("F", 0.00);

        double total = 0;
        int cnt = 0;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String currGrade = sc.nextLine();
            if (currGrade.isEmpty()) break;
            if (!letterMapping.containsKey(currGrade)) {
                throw new IllegalArgumentException("The input letter grade is invalid!");
            }
            total += (double)letterMapping.get(currGrade);
            cnt++;
        }
        System.out.println("Total grade = " + total/cnt);
        sc.close();
    }
}