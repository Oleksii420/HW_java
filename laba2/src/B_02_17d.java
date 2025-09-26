public class B_02_17d {
    public static double f(double x, double eps) {
        if (x == 0) {
            return eps >= 1 ? 1 : 0;
        }

        double sum = 0;
        int k = 0;
        while (true) {
            double term = Math.pow(-1, k) * Math.pow(x, 2 * k);
            if (Math.abs(term) <= eps) {
                sum += term;
            }
            if (Math.abs(term) < eps && Math.abs(term) < 1e-15) {
                break;
            }
            k++;
            if (k > 10000) break;
        }
        return sum;
    }

    public static void main(String[] args) {
        double x = 0.9;
        double eps = 0.01;

        double result = f(x, eps);
        System.out.println("Сума доданків: " + result);
    }
}
