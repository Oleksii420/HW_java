import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class B_06_01 {
    public static void main(String[] args) {
        String text = "Сьогодні крутий день а саме 12.03.2024, а ще є інший крутий день  ___ .____.___ для того щоб було прям дуже круто.";

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String today = currentDate.format(formatter);

        text = text
                .replaceAll("\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b", today)
                .replaceAll("_+\\s*\\.?\\s*_+\\s*\\.?\\s*_+", today)
                .replaceAll("_+", today);

        System.out.println("Після заміни:");
        System.out.println(text);
    }
}
