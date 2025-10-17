import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B_05_02 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) s = "";

        boolean a = propertyA(s);
        boolean b = propertyB(s);
        boolean c = propertyC(s);

        System.out.println("a) " + (a ? "Так" : "Ні"));
        System.out.println("b) " + (b ? "Так" : "Ні"));
        System.out.println("c) " + (c ? "Так" : "Ні"));
    }

    private static boolean propertyA(String s) {
        if (s.isEmpty() || !Character.isDigit(s.charAt(0))) return false;
        int firstDigit = s.charAt(0) - '0';
        if (firstDigit == 0) return false;
        if (s.length() - 1 != firstDigit) return false;
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) return false;
        }
        return true;
    }

    private static boolean propertyB(String s) {
        int digitCount = 0;
        int digitValue = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitCount++;
                digitValue = ch - '0';
            } else if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return digitCount == 1 && digitValue == s.length();
    }

    private static boolean propertyC(String s) {
        int sum = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) sum += ch - '0';
        }
        return sum == s.length();
    }
}
