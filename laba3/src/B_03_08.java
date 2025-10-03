import java.util.*;

class Line {
    private double a, b, c;

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }

    public boolean isParallel(Line other) {
        return this.a * other.b == this.b * other.a;
    }

    public Point intersection(Line other) {
        double det = this.a * other.b - other.a * this.b;
        if (det == 0) {
            return null;
        }
        double x = (this.b * other.c - other.b * this.c) / det;
        double y = (other.a * this.c - this.a * other.c) / det;
        return new Point(x, y);
    }
    public String toString() {
        return String.format("%.1fx + %.1fy + %.1f = 0", a, b, c);
    }
}

class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}

public class B_03_08 {
    public static List<List<Line>> groupParallel(List<Line> lines) {
        List<List<Line>> groups = new ArrayList<>();
        boolean[] visited = new boolean[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            if (visited[i]) continue;
            List<Line> group = new ArrayList<>();
            group.add(lines.get(i));
            visited[i] = true;
            for (int j = i + 1; j < lines.size(); j++) {
                if (!visited[j] && lines.get(i).isParallel(lines.get(j))) {
                    group.add(lines.get(j));
                    visited[j] = true;
                }
            }
            groups.add(group);
        }
        return groups;
    }

    public static void main(String[] args) {
        Line l1 = new Line(1, -1, 0);    // x - y = 0
        Line l2 = new Line(1, 1, -4);    // x + y - 4 = 0
        Line l3 = new Line(2, -2, 3);    // 2x - 2y + 3 = 0
        Line l4 = new Line(0, 1, -2);    // y - 2 = 0

        System.out.println(l1.intersection(l2));

        List<Line> lines = Arrays.asList(l1, l2, l3, l4);
        List<List<Line>> groups = groupParallel(lines);

        int idx = 1;
        for (List<Line> group : groups) {
            System.out.println("Група " + idx++ + ": " + group);
        }
    }
}
