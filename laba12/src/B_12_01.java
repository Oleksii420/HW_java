import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

interface Visitor {
    void visit(B_12_01.HumanitarianStudent student);
    void visit(B_12_01.NaturalStudent student);
    void visit(B_12_01.MixedStudent student);
}

public class B_12_01 {

    static abstract class Student {
        protected int credits = 0;
        protected int neededCredits;
        protected int money;
        protected boolean isExpelled = false;

        public Student(int neededCredits, int money) {
            this.neededCredits = neededCredits;
            this.money = money;
        }

        public abstract void accept(Visitor visitor);

        public void addCredits(int amount) {
            if (!isExpelled) {
                this.credits += amount;
                System.out.printf("   [Навчання] +%d кредитів. Всього: %d/%d%n", amount, credits, neededCredits);
            }
        }

        public void addMoney(int amount) {
            if (!isExpelled) {
                this.money += amount;
                System.out.printf("   [Бюджет] +%d грн. Баланс: %d%n", amount, money);
            }
        }

        public void payMoney(int amount, String reason) {
            if (isExpelled) return;

            if (this.money >= amount) {
                this.money -= amount;
                System.out.printf("   [Оплата - %s] -%d грн. Баланс: %d%n", reason, amount, money);
            } else {
                this.isExpelled = true;
                System.out.printf("   [КРИТИЧНО] Нестача коштів для '%s' (%d грн). Студента відраховано!%n", reason, amount);
            }
        }

        public boolean hasDiploma() {
            return !isExpelled && credits >= neededCredits;
        }

        public boolean isExpelled() { return isExpelled; }
    }

    static class HumanitarianStudent extends Student {
        public HumanitarianStudent(int n, int m) { super(n, m); }
        @Override public void accept(Visitor v) { v.visit(this); }
    }

    static class NaturalStudent extends Student {
        public NaturalStudent(int n, int m) { super(n, m); }
        @Override public void accept(Visitor v) { v.visit(this); }
    }

    static class MixedStudent extends Student {
        public MixedStudent(int n, int m) { super(n, m); }
        @Override public void accept(Visitor v) { v.visit(this); }
    }

    static class HumanitarianTeacher implements Visitor {
        private int credits;
        public HumanitarianTeacher(int credits) { this.credits = credits; }

        @Override public void visit(HumanitarianStudent s) { s.addCredits(credits); }

        @Override public void visit(NaturalStudent s) {
            System.out.println("   [ПОМИЛКА] Гуманітарний викладач не може вчити студента природничого напряму.");
        }

        @Override public void visit(MixedStudent s) { s.addCredits(credits); }
    }

    static class NaturalTeacher implements Visitor {
        private int credits;
        public NaturalTeacher(int credits) { this.credits = credits; }

        @Override public void visit(HumanitarianStudent s) {
            System.out.println("   [ПОМИЛКА] Природничий викладач не може вчити студента гуманітарного напряму.");
        }

        @Override public void visit(NaturalStudent s) { s.addCredits(credits); }

        @Override public void visit(MixedStudent s) { s.addCredits(credits); }
    }

    static class IncomeVisitor implements Visitor {
        private int amount;
        private String source;

        public IncomeVisitor(int amount, String source) {
            this.amount = amount;
            this.source = source;
        }

        private void give(Student s) { s.addMoney(amount); }

        @Override public void visit(HumanitarianStudent s) { give(s); }
        @Override public void visit(NaturalStudent s) { give(s); }
        @Override public void visit(MixedStudent s) { give(s); }
    }

    static class ExpenseVisitor implements Visitor {
        private int amount;
        private String reason;

        public ExpenseVisitor(int amount, String reason) {
            this.amount = amount;
            this.reason = reason;
        }

        private void take(Student s) { s.payMoney(amount, reason); }

        @Override public void visit(HumanitarianStudent s) { take(s); }
        @Override public void visit(NaturalStudent s) { take(s); }
        @Override public void visit(MixedStudent s) { take(s); }
    }

    public static void main(String[] args) {
        String filename = "input01.txt";

        System.out.println("Читання файлу: " + filename);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String typeLine = getNextLine(br);
            String creditsLine = getNextLine(br);
            String moneyLine = getNextLine(br);

            if (typeLine == null || creditsLine == null || moneyLine == null) {
                System.out.println("Помилка: Файл порожній або неповний.");
                return;
            }

            Student student = createStudent(typeLine, Integer.parseInt(creditsLine), Integer.parseInt(moneyLine));
            if (student == null) return;

            String line;
            while ((line = br.readLine()) != null && !student.isExpelled()) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Visitor v = parseCommand(line);
                if (v != null) {
                    System.out.println("> " + line);
                    student.accept(v);
                }
            }

            System.out.println("------------------------------------------------");
            if (student.hasDiploma()) {
                System.out.println("РЕЗУЛЬТАТ: Студент отримав диплом!");
            } else {
                System.out.println("РЕЗУЛЬТАТ: Студент НЕ отримав диплом.");
                if (student.isExpelled()) System.out.println("Причина: Відраховано за несплату.");
                else System.out.println("Причина: Недостатньо кредитів (" + student.credits + "/" + student.neededCredits + ").");
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Сталася помилка: " + e.getMessage());
        }
    }

    private static String getNextLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }

    private static Student createStudent(String type, int credits, int money) {
        switch (type.toLowerCase()) {
            case "humanitarian": return new HumanitarianStudent(credits, money);
            case "natural": return new NaturalStudent(credits, money);
            case "natural-humanitarian": return new MixedStudent(credits, money);
            default: System.out.println("Невідомий тип студента: " + type); return null;
        }
    }

    private static Visitor parseCommand(String line) {
        String[] parts = line.split("\\s+");
        String action = parts[0];
        String target = parts.length > 1 ? parts[1] : "";

        int val = 0;

        if (action.equals("teach")) {
            val = (parts.length > 2) ? Integer.parseInt(parts[2]) : 3;
            if (target.equals("humanitarian")) return new HumanitarianTeacher(val);
            if (target.equals("natural")) return new NaturalTeacher(val);
        }
        else if (action.equals("pay")) {
            val = Integer.parseInt(parts[2]);
            if (target.equals("hostel")) return new ExpenseVisitor(val, "Гуртожиток");
            if (target.equals("food")) return new ExpenseVisitor(val, "Їдальня");
        }
        else if (action.equals("obtain")) {
            val = (parts.length > 2) ? Integer.parseInt(parts[2]) : 1000;
            if (target.equals("scholarship")) return new IncomeVisitor(val, "Стипендія");
            if (target.equals("parents")) return new IncomeVisitor(val, "Батьки");
        }
        return null;
    }
}