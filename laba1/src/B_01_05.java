import java.util.Scanner;

public class B_01_05 {
    public static void main(String[] args) {
        int N, M;

        if (args.length == 2) {
            try {
                N = Integer.parseInt(args[0]);
                M = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return;
            }
        } else {
            Scanner sc = new Scanner(System.in);
            N = sc.nextInt();
            M = sc.nextInt();
        }

        if (N <= 0 || M <= 0) {
            return;
        }

        for (int i = 0; i < N; i++) {
            int rnd = (int) (Math.random() * M);
            System.out.println(rnd);
        }
    }
}
