import java.util.ArrayList;

public class B_06_02 {
    public static void main(String[] args) {
        String text = "+380-67-123-45-67, (093)4567890, 0501234567, +38(097) 12-34-567.";

        String phoneRegex = "\\+?\\d{1,3}?[-\\s\\(]?\\d{2,3}[\\)\\s-]?\\d{2,3}[-\\s]?\\d{2}[-\\s]?\\d{2,3}";

        ArrayList<String> phones = new ArrayList<>();
        String temp = text;

        while (temp.matches(".*" + phoneRegex + ".*")) {
            String phone = temp.replaceAll(".*?(" + phoneRegex + ").*", "$1");
            phones.add(phone);
            String escapedPhone = phone.replace("+", "\\+").replace("(", "\\(").replace(")", "\\)");
            temp = temp.replaceFirst(escapedPhone, "");
        }

        for (String p : phones) {
            System.out.println(p);
        }
    }
}
