import java.util.Scanner;

public class B_02_10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        String bin = Integer.toBinaryString(n);

        int count = 0;
        for (int i = 0; i < bin.length() - 1; i++) {
            if (bin.charAt(i) == '1' && bin.charAt(i + 1) == '1') {
                count++;
            }
        }

        System.out.println("Двійкове представлення: " + bin);
        System.out.println("Кількість входжень '11': " + count);
    }
}
