import java.util.*;

public class B_08_04 {

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double dist() {
            return Math.sqrt(x * x + y * y);
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static void main(String[] args) {

        PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingDouble(Point::dist));

        pq.add(new Point(3, 4));
        pq.add(new Point(1, 1));
        pq.add(new Point(-2, 5));
        pq.add(new Point(0, 2));
        pq.add(new Point(2, 2));

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
