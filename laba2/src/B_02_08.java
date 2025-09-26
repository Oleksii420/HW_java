import java.util.Scanner;

public class B_02_08 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введіть кількість елементів масиву: ");
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.println("Введіть елементи масиву:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int count = 0;
        for (int i = 1; i < n; i++) {
            if ((arr[i - 1] > 0 && arr[i] < 0) || (arr[i - 1] < 0 && arr[i] > 0)) {
                count++;
            }
        }

        System.out.println("Кількість змін знаку: " + count);
    }
}
