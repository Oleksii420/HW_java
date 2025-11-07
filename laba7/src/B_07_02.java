import java.io.*;
import java.util.*;

class Toy implements Serializable {
    String name;
    double price;
    int minAge;
    int maxAge;

    public Toy(String name, double price, int minAge, int maxAge) {
        this.name = name;
        this.price = price;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public boolean isForAge(int age) {
        return age >= minAge && age <= maxAge;
    }

    @Override
    public String toString() {
        return name + " (" + price + " грн, вік: " + minAge + "-" + maxAge + ")";
    }
}

public class B_07_02 {
    public static void createToyFile(String fileName, List<Toy> toys) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        for (Toy toy : toys) out.writeObject(toy);
        out.close();
    }

    public static List<Toy> readToyFile(String fileName) throws IOException, ClassNotFoundException {
        List<Toy> toys = new ArrayList<>();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        try {
            while (true) {
                Toy toy = (Toy) in.readObject();
                toys.add(toy);
            }
        } catch (EOFException e) {}
        in.close();
        return toys;
    }

    public static void writeToysForAge(String inputFile, String outputFile, int age) throws IOException, ClassNotFoundException {
        List<Toy> toys = readToyFile(inputFile);
        List<Toy> filtered = new ArrayList<>();
        for (Toy t : toys) if (t.isForAge(age)) filtered.add(t);
        createToyFile(outputFile, filtered);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String fileAll = "toys.dat";
        String fileFiltered = "toys_for_age.dat";

        List<Toy> toys = Arrays.asList(
                new Toy("М'яч", 150.0, 3, 10),
                new Toy("Лялька", 250.0, 4, 8),
                new Toy("Конструктор", 400.0, 6, 12),
                new Toy("Пірамідка", 100.0, 1, 4)
        );

        createToyFile(fileAll, toys);

        System.out.println("Іграшки у файлі:");
        List<Toy> allToys = readToyFile(fileAll);
        for (Toy t : allToys) System.out.println(t);

        int age = 5;
        writeToysForAge(fileAll, fileFiltered, age);

        System.out.println("\nІграшки для віку " + age + ":");
        List<Toy> filtered = readToyFile(fileFiltered);
        for (Toy t : filtered) System.out.println(t);
    }
}
