import java.util.*;


class Patient implements Comparable<Patient> {
    private String fullName;
    private String phone;
    private int medicalCardNumber;
    private String diagnosis;


    public Patient(String fullName, String phone, int medicalCardNumber, String diagnosis) {
        this.fullName = fullName;
        this.phone = phone;
        this.medicalCardNumber = medicalCardNumber;
        this.diagnosis = diagnosis;
    }

    public Patient(Patient other) {
        this.fullName = other.fullName;
        this.phone = other.phone;
        this.medicalCardNumber = other.medicalCardNumber;
        this.diagnosis = other.diagnosis;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getMedicalCardNumber() { return medicalCardNumber; }
    public void setMedicalCardNumber(int medicalCardNumber) { this.medicalCardNumber = medicalCardNumber; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }


    public String toString() {
        return "Пацієнт{" +
                "ПІБ='" + fullName + '\'' +
                ", Телефон='" + phone + '\'' +
                ", № картки=" + medicalCardNumber +
                ", Діагноз='" + diagnosis + '\'' +
                '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Patient)) return false;
        Patient other = (Patient) obj;
        return Objects.equals(fullName, other.fullName) &&
                Objects.equals(phone, other.phone) &&
                medicalCardNumber == other.medicalCardNumber &&
                Objects.equals(diagnosis, other.diagnosis);
    }

    public int compareTo(Patient o) {
        return this.fullName.compareTo(o.fullName);
    }
}

public class B_03_10 {
    public static List<Patient> findByDiagnosis(List<Patient> patients, String diagnosis) {
        List<Patient> result = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getDiagnosis().equalsIgnoreCase(diagnosis)) {
                result.add(p);
            }
        }
        return result;
    }

    public static List<Patient> findByCardNumberRange(List<Patient> patients, int min, int max) {
        List<Patient> result = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getMedicalCardNumber() >= min && p.getMedicalCardNumber() <= max) {
                result.add(p);
            }
        }
        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Іваненко Іван Іванович", "380991112233", 101, "Грип"));
        patients.add(new Patient("Петренко Петро Петрович", "380631234567", 150, "Ангіна"));
        patients.add(new Patient("Сидоренко Олег Васильович", "380501234567", 120, "Грип"));
        patients.add(new Patient("Мельник Марія Іванівна", "380671112233", 180, "Астма"));

        System.out.println("Пацієнти з діагнозом 'Грип':");
        List<Patient> fluPatients = findByDiagnosis(patients, "Грип");
        for (Patient p : fluPatients) {
            System.out.println(p);
        }

        System.out.println("\nПацієнти з № картки у інтервалі [110, 160]:");
        List<Patient> filtered = findByCardNumberRange(patients, 110, 160);
        for (Patient p : filtered) {
            System.out.println(p);
        }
    }
}
