import java.io.*;
import java.util.*;

public class B_05_10 {
    static class Fruit {
        String type;
        String variety;
        String country;

        Fruit(String type, String variety, String country) {
            this.type = type;
            this.variety = variety;
            this.country = country;
        }
    }

    public static void main(String[] args) {
        List<Fruit> fruits = readFruits("fruits.txt");
        if (fruits.isEmpty()) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть назву країни: ");
        String country = sc.nextLine().trim();

        List<Fruit> fromCountry = searchByCountry(fruits, country);
        writeFruitsToFile(fromCountry, "fruits_from_country.txt");

        Map<String, Integer> countByType = countByType(fruits);
        writeCountByTypeToFile(countByType, "fruits_count_by_type.txt");

        System.out.println("Результати записано у файли:");
        System.out.println("- fruits_from_country.txt");
        System.out.println("- fruits_count_by_type.txt");
    }

    private static List<Fruit> readFruits(String filename) {
        List<Fruit> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    list.add(new Fruit(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
        return list;
    }

    private static List<Fruit> searchByCountry(List<Fruit> fruits, String country) {
        List<Fruit> result = new ArrayList<>();
        for (Fruit f : fruits) {
            if (f.country.equalsIgnoreCase(country)) {
                result.add(f);
            }
        }
        return result;
    }

    private static Map<String, Integer> countByType(List<Fruit> fruits) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Fruit f : fruits) {
            map.put(f.type, map.getOrDefault(f.type, 0) + 1);
        }
        return map;
    }

    private static void writeFruitsToFile(List<Fruit> fruits, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Fruit f : fruits) {
                pw.println(f.type + " " + f.variety + " " + f.country);
            }
        } catch (IOException e) {
            System.out.println("Помилка запису файлу: " + e.getMessage());
        }
    }

    private static void writeCountByTypeToFile(Map<String, Integer> countByType, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
                pw.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Помилка запису файлу: " + e.getMessage());
        }
    }
}
