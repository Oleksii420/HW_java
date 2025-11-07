import java.io.*;
import java.util.*;

public class B_07_01 {

    public static void createFileF(String fileName, double[] numbers) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
        for (double num : numbers) {
            out.writeDouble(num);
        }
        out.close();
        System.out.println("Файл " + fileName);
    }

    public static double[] readFile(String fileName) throws IOException {
        List<Double> list = new ArrayList<>();
        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        while (in.available() > 0) {
            list.add(in.readDouble());
        }
        in.close();
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    public static void createFileG(String fileF, String fileG, double a) throws IOException {
        double[] numbers = readFile(fileF);
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileG));
        for (double num : numbers) {
            if (num > a) out.writeDouble(num);
        }
        out.close();
        System.out.println("Файл " + fileG);
    }

    public static void main(String[] args) throws IOException {
        String fileF = "F";
        String fileG = "G";

        double[] numbers = {3.2, -1.4, 5.0, 7.8, 2.2};
        createFileF(fileF, numbers);

        System.out.println("числа з файлу F:");
        double[] read = readFile(fileF);
        for (double d : read) System.out.print(d + " ");
        System.out.println();

        double a = 3.0;
        createFileG(fileF, fileG, a);

        System.out.println("Числа з файлу G (більші за " + a + "):");
        double[] gRead = readFile(fileG);
        for (double d : gRead) System.out.print(d + " ");
    }
}
