import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B_05_01 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) s = "";

        else {
            String result = removeParenthesesContent(s);
            System.out.println(result);
        }
    }


    private static boolean checkBrackets(String s) {
        int openCount = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                if (openCount > 0) return false;
                openCount++;
            } else if (ch == ')') {
                if (openCount == 0) return false;
                openCount--;
            }
        }
        return openCount == 0;
    }

    private static String removeParenthesesContent(String s) {
        StringBuilder sb = new StringBuilder();
        boolean inside = false;
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                inside = true;
            } else if (ch == ')') {
                inside = false;
            } else if (!inside) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
