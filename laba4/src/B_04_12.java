import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

abstract class ScientificJournal {
    private String name;
    private int articleCount;
    private double citationIndex;

    public ScientificJournal(String name, int articleCount, double citationIndex) {
        this.name = name;
        this.articleCount = articleCount;
        this.citationIndex = citationIndex;
    }

    public abstract double calculatePopularity();

    public String getName() {
        return name;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public double getCitationIndex() {
        return citationIndex;
    }

    @Override
    public String toString() {
        return String.format("Журнал: %-20s | Статей: %-4d | Індекс: %-4.1f | Популярність: %-6.1f",
                name, articleCount, citationIndex, calculatePopularity());
    }
}

class NationalJournal extends ScientificJournal {
    public NationalJournal(String name, int articleCount, double citationIndex) {
        super(name, articleCount, citationIndex);
    }

    @Override
    public double calculatePopularity() {
        return (getCitationIndex() * 10) + getArticleCount();
    }

    @Override
    public String toString() {
        return "[National]      " + super.toString();
    }
}

class InternationalJournal extends ScientificJournal {
    public InternationalJournal(String name, int articleCount, double citationIndex) {
        super(name, articleCount, citationIndex);
    }

    @Override
    public double calculatePopularity() {
        return (getCitationIndex() * 25) + (getArticleCount() * 1.5);
    }

    @Override
    public String toString() {
        return "[International] " + super.toString();
    }
}

class ScientometricDatabase {
    private List<ScientificJournal> journals;

    public ScientometricDatabase() {
        this.journals = new ArrayList<>();
    }

    public void addJournal(ScientificJournal journal) {
        journals.add(journal);
    }

    public double calculateAverageCitationIndex() {
        if (journals.isEmpty()) return 0.0;

        double sum = 0;
        for (ScientificJournal journal : journals) {
            sum += journal.getCitationIndex();
        }
        return sum / journals.size();
    }

    public void sortByArticleCount() {
        Collections.sort(journals, Comparator.comparingInt(ScientificJournal::getArticleCount).reversed());
    }

    public List<ScientificJournal> findByPopularityRange(double minPop, double maxPop) {
        List<ScientificJournal> result = new ArrayList<>();
        for (ScientificJournal journal : journals) {
            double pop = journal.calculatePopularity();
            if (pop >= minPop && pop <= maxPop) {
                result.add(journal);
            }
        }
        return result;
    }

    public void printAll() {
        for (ScientificJournal j : journals) {
            System.out.println(j);
        }
    }
}

public class B_04_12 {
    public static void main(String[] args) {
        ScientometricDatabase db = new ScientometricDatabase();

        db.addJournal(new NationalJournal("Visnyk KPI", 120, 1.5));
        db.addJournal(new InternationalJournal("Nature Physics", 200, 15.0));
        db.addJournal(new NationalJournal("Economics UA", 80, 0.8));
        db.addJournal(new InternationalJournal("IEEE Software", 150, 8.5));
        db.addJournal(new NationalJournal("Pedagogy Today", 50, 0.5));

        System.out.println("--- Початковий список журналів ---");
        db.printAll();

        double avgCitation = db.calculateAverageCitationIndex();
        System.out.printf("\n--- Середній індекс цитування: %.2f ---\n", avgCitation);

        System.out.println("\n--- Після сортування за кількістю статей (desc) ---");
        db.sortByArticleCount();
        db.printAll();

        double minP = 100.0;
        double maxP = 400.0;
        System.out.printf("\n--- Журнали з популярністю від %.0f до %.0f ---\n", minP, maxP);

        List<ScientificJournal> found = db.findByPopularityRange(minP, maxP);
        if (found.isEmpty()) {
            System.out.println("Журналів не знайдено.");
        } else {
            for (ScientificJournal j : found) {
                System.out.println(j);
            }
        }
    }
}